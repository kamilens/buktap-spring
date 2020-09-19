package com.kamilens.buktap.service.impl;

import com.kamilens.buktap.entity.User;
import com.kamilens.buktap.repository.UserRepository;
import com.kamilens.buktap.security.JwtAuthException;
import com.kamilens.buktap.security.JwtTokenProvider;
import com.kamilens.buktap.service.AuthService;
import com.kamilens.buktap.service.dto.AuthenticationDTO;
import com.kamilens.buktap.service.dto.UserRegisterDTO;
import com.kamilens.buktap.service.mapper.UserMapper;
import com.kamilens.buktap.web.rest.vm.IdVM;
import com.kamilens.buktap.web.rest.vm.JWTTokenVM;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    @Override
    public IdVM register(UserRegisterDTO userRegisterDTO) {
        return null;
    }

    @Override
    public JWTTokenVM authenticate(AuthenticationDTO authenticationDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword())
            );
            User user = userRepository.findByEmail(authenticationDTO.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
            String token = jwtTokenProvider.createToken(authenticationDTO.getEmail(), user.getRole().name());

            return JWTTokenVM.builder()
                    .email(authenticationDTO.getEmail())
                    .token(token)
                    .build();
        } catch (AuthenticationException e) {
            throw new JwtAuthException("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(httpServletRequest, httpServletResponse, null);
    }

}
