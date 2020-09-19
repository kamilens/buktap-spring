package com.kamilens.buktap.service;

import com.kamilens.buktap.service.dto.AuthenticationDTO;
import com.kamilens.buktap.service.dto.UserRegisterDTO;
import com.kamilens.buktap.web.rest.vm.IdVM;
import com.kamilens.buktap.web.rest.vm.JWTTokenVM;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    IdVM register(UserRegisterDTO userRegisterDTO);

    JWTTokenVM authenticate(AuthenticationDTO authenticationDTO);

    void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

}
