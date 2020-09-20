package com.kamilens.buktap.service.impl;

import com.kamilens.buktap.entity.User;
import com.kamilens.buktap.entity.VerificationToken;
import com.kamilens.buktap.entity.enumeration.UserStatus;
import com.kamilens.buktap.entity.pojo.NotificationEmail;
import com.kamilens.buktap.repository.UserRepository;
import com.kamilens.buktap.repository.VerificationTokenRepository;
import com.kamilens.buktap.security.jwt.JwtAuthException;
import com.kamilens.buktap.security.jwt.JwtTokenProvider;
import com.kamilens.buktap.service.AuthService;
import com.kamilens.buktap.service.MailService;
import com.kamilens.buktap.service.RefreshTokenService;
import com.kamilens.buktap.service.dto.AuthenticationDTO;
import com.kamilens.buktap.service.dto.RefreshTokenDTO;
import com.kamilens.buktap.service.dto.UserRegisterDTO;
import com.kamilens.buktap.service.mapper.UserMapper;
import com.kamilens.buktap.web.rest.error.NotFoundException;
import com.kamilens.buktap.web.rest.vm.IdVM;
import com.kamilens.buktap.web.rest.vm.JWTTokenVM;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final RefreshTokenService refreshTokenService;

    private static final class UserNotFoundException extends NotFoundException {
        public UserNotFoundException() {
            super("User not found");
        }
    }

    @Override
    public IdVM register(UserRegisterDTO userRegisterDTO) {
        userRegisterDTO.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        User user = userMapper.registerDTOToEntity(userRegisterDTO);

        User createdUser = userRepository.save(user);

        String token = generateVerificationToken(user);

        mailService.sendMail(
                NotificationEmail.builder()
                        .subject("Please activate your account")
                        .recipient(user.getEmail())
                        .body("Thank you for singing up to RedditClone, " +
                                "please click on the below url to activate your account: " +
                                "http://localhost:8080/api/auth/verification/" + token)
                        .build()
        );

        return IdVM.builder().id(createdUser.getId()).build();
    }

    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken =
                VerificationToken.builder()
                        .token(token)
                        .user(user)
                        .build();

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(UserNotFoundException::new);
    }

    public IdVM verify(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(UserNotFoundException::new);

        String email = verificationToken.getUser().getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        return IdVM.builder().id(user.getId()).build();
    }

    @Override
    public JWTTokenVM authenticate(AuthenticationDTO authenticationDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword())
            );
            User user = userRepository.findByEmail(authenticationDTO.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
            String token = jwtTokenProvider.createToken(authenticationDTO.getEmail(), user.getRole());

            boolean rememberMe = authenticationDTO.getRememberMe();

            return JWTTokenVM.builder()
                    .email(authenticationDTO.getEmail())
                    .token(token)
                    .refreshToken(rememberMe ? refreshTokenService.generateRefreshToken().getToken() : null)
                    .expiresAt(Instant.now().plusMillis(jwtTokenProvider.getValidityInMilliseconds()))
                    .build();
        } catch (AuthenticationException e) {
            throw new JwtAuthException("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public JWTTokenVM refresh(RefreshTokenDTO refreshTokenDTO) {
        refreshTokenService.validateRefreshToken(refreshTokenDTO.getRefreshToken());

        User user = userRepository.findByEmail(refreshTokenDTO.getEmail())
                .orElseThrow(UserNotFoundException::new);

        String token = jwtTokenProvider.createToken(refreshTokenDTO.getEmail(), user.getRole());
        return JWTTokenVM.builder()
                .token(token)
                .refreshToken(refreshTokenDTO.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtTokenProvider.getValidityInMilliseconds()))
                .email(refreshTokenDTO.getEmail())
                .build();
    }

    @Override
    public IdVM logout(RefreshTokenDTO refreshTokenDTO,
                       HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) {
        User user = userRepository.findByEmail(refreshTokenDTO.getEmail())
                .orElseThrow(UserNotFoundException::new);

        refreshTokenService.deleteRefreshToken(refreshTokenDTO.getRefreshToken());

        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(httpServletRequest, httpServletResponse, null);

        return IdVM.builder().id(user.getId()).build();
    }

}
