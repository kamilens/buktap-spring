package com.kamilens.buktap.service.impl;

import com.kamilens.buktap.entity.RefreshToken;
import com.kamilens.buktap.repository.RefreshTokenRepository;
import com.kamilens.buktap.service.RefreshTokenService;
import com.kamilens.buktap.web.rest.error.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private static final class RefreshTokenException extends AuthException {
        public RefreshTokenException() {
            super("Invalid refresh Token");
        }
    }

    @Override
    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreationDate(Date.from(Instant.now()));

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token).orElseThrow(RefreshTokenException::new);
    }

    @Override
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

}

