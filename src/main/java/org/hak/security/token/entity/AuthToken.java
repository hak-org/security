package org.hak.security.token.entity;

import java.time.LocalDateTime;

public interface AuthToken {

    String getUid();
    String getAccessToken();

    String getRefreshToken();

    LocalDateTime getCreatedAt();

    LocalDateTime getExpiredAt();

    LocalDateTime getRefreshExpiredAt();

}
