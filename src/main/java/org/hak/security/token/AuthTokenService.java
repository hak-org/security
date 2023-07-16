package org.hak.security.token;

import org.hak.security.token.entity.AuthToken;

import java.util.List;

public interface AuthTokenService {

    AuthToken generate(AuthToken input);
    String verify(String accessToken) throws AuthTokenNotFoundException, AuthTokenExpiredException;
    AuthToken refresh(String refreshToken) throws AuthTokenNotFoundException, AuthTokenExpiredException;
    void revoke(String accessToken) throws AuthTokenNotFoundException;

}
