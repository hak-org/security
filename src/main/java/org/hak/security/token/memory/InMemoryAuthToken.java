package org.hak.security.token.memory;

import java.time.LocalDateTime;

public class InMemoryAuthToken {

    private String uid;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime refreshExpiredAt;
    public InMemoryAuthToken(String uid, String accessToken, String refreshToken,
                             LocalDateTime createdAt, LocalDateTime expiredAt, LocalDateTime refreshExpiredAt) {
        this.uid = uid;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.refreshExpiredAt = refreshExpiredAt;
    }

    public String getUid() {
        return uid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public LocalDateTime getRefreshExpiredAt() {
        return refreshExpiredAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public void setRefreshExpiredAt(LocalDateTime refreshExpiredAt) {
        this.refreshExpiredAt = refreshExpiredAt;
    }
}
