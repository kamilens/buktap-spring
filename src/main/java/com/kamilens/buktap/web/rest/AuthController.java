package com.kamilens.buktap.web.rest;

import com.kamilens.buktap.service.dto.AuthenticationDTO;
import com.kamilens.buktap.service.dto.UserRegisterDTO;
import com.kamilens.buktap.web.rest.vm.IdVM;
import com.kamilens.buktap.web.rest.vm.JWTTokenVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping("/api/auth")
@RestController
public interface AuthController {

    @PostMapping("/register")
    ResponseEntity<IdVM> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO);

    @PostMapping("/authenticate")
    ResponseEntity<JWTTokenVM> authenticate(@RequestBody @Valid AuthenticationDTO authenticationDTO);

    @PostMapping("/logout")
    ResponseEntity<Void> logout(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse);

}
