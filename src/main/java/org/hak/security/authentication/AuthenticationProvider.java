package org.hak.security.authentication;

import org.hak.security.account.entity.Account;
import org.hak.security.authentication.entity.Authentication;
import org.hak.security.authentication.entity.Me;
import org.hak.security.token.entity.AuthToken;

public interface AuthenticationProvider {

    Account signup(Authentication form);

    AuthToken login(Authentication form);

    void logout(String accessToken);

    void checkToken(String accessToken);

    AuthToken refreshToken(String refreshToken);

    Me me(String accessToken);

}
