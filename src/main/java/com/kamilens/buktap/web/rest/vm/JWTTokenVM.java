package com.kamilens.buktap.web.rest.vm;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class JWTTokenVM {

    private String email;
    private String token;
    private String refreshToken;
    private Instant expiresAt;

}
