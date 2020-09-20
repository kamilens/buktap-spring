package com.kamilens.buktap.service.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RefreshTokenDTO {

    @NotBlank
    @NotNull
    @NotEmpty
    private String refreshToken;

    @NotBlank
    @NotNull
    @NotEmpty
    @Email
    private String email;

}
