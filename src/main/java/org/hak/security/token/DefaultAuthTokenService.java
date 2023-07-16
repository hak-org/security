package org.hak.security.token;

import org.hak.core.generator.StringGenerator;
import org.hak.security.token.entity.AuthToken;
import org.hak.security.token.entity.AuthTokens;

import java.time.LocalDateTime;

public class DefaultAuthTokenService implements AuthTokenService {

    private final AuthTokens tokens;
    private final StringGenerator accessTokens;
    private final StringGenerator refreshTokens;
    private final long expiredMinutes;
    private final long refreshExpiredMinutes;

    public DefaultAuthTokenService(AuthTokens tokens, StringGenerator accessTokens, StringGenerator refreshTokens,
                                   long expiredMinutes, long refreshExpiredMinutes) {
        this.tokens = tokens;
        this.accessTokens = accessTokens;
        this.refreshTokens = refreshTokens;
        this.expiredMinutes = expiredMinutes;
        this.refreshExpiredMinutes = refreshExpiredMinutes;
    }

    @Override
    public AuthToken generate(AuthToken form) {

        final String uid = form.getUid();
        tokens.retrieveAllByUid(uid).forEach(token -> tokens.delete(token.getAccessToken()));

        String accessToken = accessTokens.generate();
        String refreshToken = refreshTokens.generate();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireAt = now.plusMinutes(expiredMinutes);
        LocalDateTime refreshExpiredAt = now.plusMinutes(refreshExpiredMinutes);

        DefaultAuthToken newToken = DefaultAuthToken.newInstance(
                uid, accessToken, refreshToken,
                now, expireAt, refreshExpiredAt
        );
        return tokens.create(newToken);
    }

    @Override
    public String verify(String accessToken) throws AuthTokenNotFoundException, AuthTokenExpiredException {

        AuthToken authToken = tokens.retrieve(accessToken)
                .orElseThrow(AuthTokenNotFoundException::new);

        if (LocalDateTime.now().isAfter(authToken.getExpiredAt())) {
            throw new AuthTokenExpiredException();
        }

        return authToken.getUid();
    }

    @Override
    public AuthToken refresh(String refreshToken) throws AuthTokenNotFoundException, AuthTokenExpiredException {

        AuthToken authToken = tokens.retrieveByRefreshToken(refreshToken)
                .orElseThrow(AuthTokenNotFoundException::new);

        if (LocalDateTime.now().isAfter(authToken.getRefreshExpiredAt())) {
            throw new AuthTokenExpiredException();
        }

        revoke(authToken.getAccessToken());
        return generate(DefaultAuthToken.generateForm(authToken.getUid()));
    }

    @Override
    public void revoke(String accessToken) throws AuthTokenNotFoundException {
        tokens.retrieve(accessToken).orElseThrow(AuthTokenNotFoundException::new);
        tokens.delete(accessToken);
    }
}
