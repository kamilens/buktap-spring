package com.kamilens.buktap.web.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public abstract class AuthException extends RuntimeException {

    protected AuthException(String message) {
        super(message);
    }

}
