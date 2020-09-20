package com.kamilens.buktap.web.rest;

import com.kamilens.buktap.service.dto.AuthenticationDTO;
import com.kamilens.buktap.service.dto.RefreshTokenDTO;
import com.kamilens.buktap.service.dto.UserRegisterDTO;
import com.kamilens.buktap.web.rest.vm.IdVM;
import com.kamilens.buktap.web.rest.vm.JWTTokenVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "Authentication controller")
@RequestMapping("/api/auth")
public interface AuthController {

    @ApiOperation("User sign-up")
    @PostMapping("/register")
    ResponseEntity<IdVM> register(@RequestBody UserRegisterDTO userRegisterDTO);

    @ApiOperation("User verify")
    @PostMapping("/verify/{token}")
    ResponseEntity<IdVM> verify(@PathVariable String token);

    @ApiOperation("User sign-in")
    @PostMapping("/authenticate")
    ResponseEntity<JWTTokenVM> authenticate(@RequestBody AuthenticationDTO authenticationDTO);

    @ApiOperation("Refresh token")
    @PostMapping("/refresh")
    ResponseEntity<JWTTokenVM> refresh(@RequestBody RefreshTokenDTO refreshTokenDTO);

    @ApiOperation("User logout")
    @PostMapping("/logout")
    ResponseEntity<IdVM> logout(@RequestBody RefreshTokenDTO refreshTokenDTO,
                                HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse);

}
