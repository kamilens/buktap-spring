package com.kamilens.buktap.web.rest.impl;

import com.kamilens.buktap.service.AuthService;
import com.kamilens.buktap.service.dto.AuthenticationDTO;
import com.kamilens.buktap.service.dto.UserRegisterDTO;
import com.kamilens.buktap.web.rest.AuthController;
import com.kamilens.buktap.web.rest.vm.IdVM;
import com.kamilens.buktap.web.rest.vm.JWTTokenVM;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    public ResponseEntity<IdVM> register(UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.ok(authService.register(userRegisterDTO));
    }

    public ResponseEntity<JWTTokenVM> authenticate(AuthenticationDTO authenticationDTO) {
        return ResponseEntity.ok(authService.authenticate(authenticationDTO));
    }

    public ResponseEntity<Void> logout(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse) {
        authService.logout(httpServletRequest, httpServletResponse);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
