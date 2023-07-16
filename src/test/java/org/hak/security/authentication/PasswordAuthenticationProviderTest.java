package org.hak.security.authentication;

import org.hak.core.generator.ShortUuidGenerator;
import org.hak.security.account.AccountService;
import org.hak.security.account.DefaultAccountService;
import org.hak.security.account.entity.Account;
import org.hak.security.account.memory.InMemoryAccountRepository;
import org.hak.security.authentication.entity.Me;
import org.hak.security.token.AuthTokenNotFoundException;
import org.hak.security.token.DefaultAuthTokenService;
import org.hak.security.token.entity.AuthToken;
import org.hak.security.token.memory.InMemoryAuthTokenRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PasswordAuthenticationProviderTest {

    static PasswordAuthenticationProvider provider;

    @BeforeAll
    static void setup() {
        DefaultAccountService accountService = new DefaultAccountService(new InMemoryAccountRepository(new HashMap<>()));
        DefaultAuthTokenService authTokenService = new DefaultAuthTokenService(new InMemoryAuthTokenRepository(new HashMap<>()),
                new ShortUuidGenerator(), new ShortUuidGenerator(), 10, 100);
        provider = new PasswordAuthenticationProvider(accountService, authTokenService);
    }

    @Test
    void signup() {
        PasswordAuthentication given = PasswordAuthentication.signupForm(
                "hakchangs", "1234", "1234"
        );
        Account actual = provider.signup(given);
        System.out.println(actual);
        assertNotNull(actual);
    }

    @Test
    void login() {
        Account sample = provider.signup(PasswordAuthentication.signupForm(
                "hakchangs", "1234", "1234"
        ));
        System.out.println(sample);
        PasswordAuthentication given = PasswordAuthentication.loginForm(
                "hakchangs", "1234"
        );
        AuthToken actual = provider.login(given);
        System.out.println(actual);
        assertNotNull(actual);
        assertNotNull(actual.getAccessToken());
        assertEquals(sample.getUid(), actual.getUid());
    }

    @Test
    void logout() {
        Account sample = provider.signup(PasswordAuthentication.signupForm(
                "hakchangs", "1234", "1234"
        ));
        System.out.println(sample);
        AuthToken given = provider.login(PasswordAuthentication.loginForm(
                "hakchangs", "1234"
        ));
        provider.logout(given.getAccessToken());
        assertThrows(AuthTokenNotFoundException.class, () -> provider.checkToken(given.getAccessToken()));
    }

    @Test
    void checkToken() {
        Account sample = provider.signup(PasswordAuthentication.signupForm(
                "hakchangs", "1234", "1234"
        ));
        System.out.println(sample);
        AuthToken given = provider.login(PasswordAuthentication.loginForm(
                "hakchangs", "1234"
        ));
        provider.checkToken(given.getAccessToken());
    }

    @Test
    void refreshToken() {
        Account sample = provider.signup(PasswordAuthentication.signupForm(
                "hakchangs", "1234", "1234"
        ));
        System.out.println(sample);
        AuthToken given = provider.login(PasswordAuthentication.loginForm(
                "hakchangs", "1234"
        ));
        System.out.println(given);
        AuthToken actual = provider.refreshToken(given.getRefreshToken());
        System.out.println(actual);
        assertNotNull(actual);
        assertNotEquals(given, actual);
        assertEquals(given.getUid(), actual.getUid());
        assertNotEquals(given.getAccessToken(), actual.getAccessToken());
        assertNotEquals(given.getRefreshToken(), actual.getRefreshToken());
    }

    @Test
    void me() {
        Account sample = provider.signup(PasswordAuthentication.signupForm(
                "hakchangs", "1234", "1234"
        ));
        System.out.println(sample);
        AuthToken given = provider.login(PasswordAuthentication.loginForm(
                "hakchangs", "1234"
        ));
        System.out.println(given);
        Me actual = provider.me(given.getAccessToken());
        System.out.println(actual);
        assertNotNull(actual);
        assertEquals(given.getUid(), actual.getMe().getUid());
        assertEquals(sample.getUsername(), actual.getMe().getUsername());
    }
}