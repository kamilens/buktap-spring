package com.kamilens.buktap.entity.enumeration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserStatus {

    ACTIVE("ACTIVE"),
    WAITING_FOR_VERIFICATION("WAITING_FOR_VERIFICATION"),
    BANNED("BANNED");

    private final String status;

}
