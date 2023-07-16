package org.hak.security.token;

import org.hak.security.token.entity.AuthToken;

import java.time.LocalDateTime;

public class DefaultAuthToken implements AuthToken {

    private String uid;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime refreshExpiredAt;

    private DefaultAuthToken() {}

    @Override
    public String getUid() {
        return uid;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    @Override
    public LocalDateTime getRefreshExpiredAt() {
        return refreshExpiredAt;
    }

    public static DefaultAuthToken generateForm(String uid) {
        DefaultAuthToken authToken = new DefaultAuthToken();
        authToken.uid = uid;
        return authToken;
    }

    public static DefaultAuthToken newInstance(String uid, String accessToken, String refreshToken,
                                               LocalDateTime createdAt, LocalDateTime expiredAt, LocalDateTime refreshExpiredAt) {
        DefaultAuthToken authToken = new DefaultAuthToken();
        authToken.uid = uid;
        authToken.accessToken = accessToken;
        authToken.refreshToken = refreshToken;
        authToken.createdAt = createdAt;
        authToken.expiredAt = expiredAt;
        authToken.refreshExpiredAt = refreshExpiredAt;
        return authToken;
    }

    @Override
    public String toString() {
        return "DefaultAuthToken{" +
                "uid='" + uid + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", createdAt=" + createdAt +
                ", expiredAt=" + expiredAt +
                ", refreshExpiredAt=" + refreshExpiredAt +
                '}';
    }
}
