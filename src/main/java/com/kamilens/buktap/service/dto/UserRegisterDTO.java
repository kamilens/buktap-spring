package com.kamilens.buktap.service.dto;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;
import java.util.Date;

@Data
public class UserRegisterDTO {

    @NotNull
    @NotBlank
    @NotEmpty
    private String fullName;

    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 7)
    private String password;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date birthDate;

}
