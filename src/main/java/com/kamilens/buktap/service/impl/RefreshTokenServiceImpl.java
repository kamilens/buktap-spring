package com.kamilens.buktap.service.impl;

import com.kamilens.buktap.entity.RefreshToken;
import com.kamilens.buktap.repository.RefreshTokenRepository;
import com.kamilens.buktap.service.RefreshTokenService;
import com.kamilens.buktap.web.rest.error.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    private static final class RefreshTokenException extends AuthException {
        public RefreshTokenException() {
            super("Invalid refresh Token");
        }
    }

    @Override
    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .creationDate(Date.from(Instant.now()))
                .build();

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

    @Scheduled(cron = "0 0 * * * *")
    public void removeNotActivatedUsers() {
        refreshTokenRepository
                .deleteAllByCreationDateAfter(Date.from(Instant.now().plus(refreshExpiration, ChronoUnit.MINUTES)));
    }

}

