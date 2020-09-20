package com.kamilens.buktap.web.rest.impl;

import com.kamilens.buktap.service.AuthService;
import com.kamilens.buktap.service.dto.AuthenticationDTO;
import com.kamilens.buktap.service.dto.RefreshTokenDTO;
import com.kamilens.buktap.service.dto.UserRegisterDTO;
import com.kamilens.buktap.web.rest.AuthController;
import com.kamilens.buktap.web.rest.vm.IdVM;
import com.kamilens.buktap.web.rest.vm.JWTTokenVM;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    public ResponseEntity<IdVM> register(@Valid UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.ok(authService.register(userRegisterDTO));
    }

    @Override
    public ResponseEntity<IdVM> verify(String token) {
        return ResponseEntity.ok(authService.verify(token));
    }

    public ResponseEntity<JWTTokenVM> authenticate(@Valid AuthenticationDTO authenticationDTO) {
        return ResponseEntity.ok(authService.authenticate(authenticationDTO));
    }

    @Override
    public ResponseEntity<JWTTokenVM> refresh(@Valid RefreshTokenDTO refreshTokenDTO) {
        return ResponseEntity.ok(authService.refresh(refreshTokenDTO));
    }

    @Override
    public ResponseEntity<IdVM> logout(@Valid RefreshTokenDTO refreshTokenDTO, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return ResponseEntity.ok(authService.logout(refreshTokenDTO, httpServletRequest, httpServletResponse));
    }

}
