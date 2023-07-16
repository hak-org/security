package org.hak.security.token;

import org.hak.core.generator.ShortUuidGenerator;
import org.hak.security.token.entity.AuthToken;
import org.hak.security.token.memory.InMemoryAuthTokenRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DefaultAuthTokenServiceTest {

    static DefaultAuthTokenService service;

    @BeforeAll
    static void setup() {
        InMemoryAuthTokenRepository authTokens = new InMemoryAuthTokenRepository(new HashMap<>());
        service = new DefaultAuthTokenService(authTokens, new ShortUuidGenerator(), new ShortUuidGenerator(),
                10, 60);
    }

    @Test
    void generate() {
        DefaultAuthToken given = DefaultAuthToken.generateForm("uid_1234");
        AuthToken actual = service.generate(given);
        System.out.println(actual);
        assertNotNull(actual);
    }

    @Test
    void verify() {
        DefaultAuthToken sample = DefaultAuthToken.generateForm("uid_1234");
        AuthToken given = service.generate(sample);
        System.out.println(given);
        String actual = service.verify(given.getAccessToken());
        System.out.println(actual);
        assertNotNull(actual);
        assertEquals(sample.getUid(), actual);
    }

    @Test
    void refresh() {
        DefaultAuthToken sample = DefaultAuthToken.generateForm("uid_1234");
        AuthToken given = service.generate(sample);
        System.out.println(given);
        AuthToken actual = service.refresh(given.getRefreshToken());
        System.out.println(actual);
        assertNotNull(actual);
        assertEquals(sample.getUid(), actual.getUid());
        assertNotEquals(sample.getAccessToken(), actual.getAccessToken());
        assertThrows(AuthTokenNotFoundException.class, () -> service.verify(given.getAccessToken()));
    }

    @Test
    void revoke() {
        DefaultAuthToken sample = DefaultAuthToken.generateForm("uid_1234");
        AuthToken given = service.generate(sample);
        System.out.println(given);
        final String accessToken = given.getAccessToken();
        service.revoke(accessToken);
        assertThrows(AuthTokenNotFoundException.class, () -> service.verify(given.getAccessToken()));
    }
}