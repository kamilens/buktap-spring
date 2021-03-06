package com.kamilens.buktap.service;

import com.kamilens.buktap.entity.User;
import com.kamilens.buktap.service.dto.AuthenticationDTO;
import com.kamilens.buktap.service.dto.RefreshTokenDTO;
import com.kamilens.buktap.service.dto.UserRegisterDTO;
import com.kamilens.buktap.web.rest.vm.IdVM;
import com.kamilens.buktap.web.rest.vm.JWTTokenVM;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    IdVM register(UserRegisterDTO userRegisterDTO);

    String generateVerificationToken(User user);

    User getCurrentUser();

    IdVM verify(String token);

    JWTTokenVM authenticate(AuthenticationDTO authenticationDTO);

    JWTTokenVM refresh(RefreshTokenDTO refreshTokenDTO);

    IdVM logout(RefreshTokenDTO refreshTokenDTO,
                HttpServletRequest httpServletRequest,
                HttpServletResponse httpServletResponse);

}
