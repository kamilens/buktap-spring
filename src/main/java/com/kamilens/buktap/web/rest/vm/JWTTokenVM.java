package com.kamilens.buktap.web.rest.vm;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JWTTokenVM {

    private String email;
    private String token;

}
