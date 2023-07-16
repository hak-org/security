package org.hak.security.token.memory;

import org.hak.security.token.DefaultAuthToken;
import org.hak.security.token.entity.AuthToken;
import org.hak.security.token.entity.AuthTokens;

import java.util.*;

public class InMemoryAuthTokenRepository implements AuthTokens {

    private final Map<String, InMemoryAuthToken> storage;
    private final Map<String, String> refreshTokenAccessTokenIndex;

    public InMemoryAuthTokenRepository(Map<String, InMemoryAuthToken> storage) {
        this.storage = storage;
        this.refreshTokenAccessTokenIndex = new HashMap<>();
    }

    @Override
    public boolean exists(String accessToken) {
        return storage.containsKey(accessToken);
    }

    @Override
    public AuthToken create(AuthToken input) {
        InMemoryAuthToken newToken = new InMemoryAuthToken(
                input.getUid(), input.getAccessToken(), input.getRefreshToken(),
                input.getCreatedAt(), input.getExpiredAt(), input.getRefreshExpiredAt());
        storage.put(input.getAccessToken(), newToken);
        refreshTokenAccessTokenIndex.put(input.getRefreshToken(), input.getAccessToken());
        return input;
    }

    @Override
    public Optional<AuthToken> retrieve(String accessToken) {
        InMemoryAuthToken found = storage.get(accessToken);
        if (found == null) {
            return Optional.empty();
        }
        return Optional.of(found).map(memory -> DefaultAuthToken.newInstance(
                memory.getUid(), memory.getAccessToken(), memory.getRefreshToken(),
                memory.getCreatedAt(), memory.getExpiredAt(), memory.getRefreshExpiredAt()));
    }

    @Override
    public Optional<AuthToken> retrieveByRefreshToken(String refreshToken) {
        String accessToken = refreshTokenAccessTokenIndex.get(refreshToken);
        return retrieve(accessToken);
    }

    @Override
    public List<? extends AuthToken> retrieveAllByUid(String uid) {
        return storage.values().stream()
                .filter(token -> token.getUid().equals(uid))
                .map(token -> DefaultAuthToken.newInstance(
                        token.getUid(), token.getAccessToken(), token.getRefreshToken(),
                        token.getCreatedAt(), token.getExpiredAt(), token.getRefreshExpiredAt()
                )).toList();
    }

    @Override
    public void update(AuthToken input) {
        InMemoryAuthToken found = storage.get(input.getAccessToken());
        if (found == null) {
            throw new NoSuchElementException();
        }
        found.setCreatedAt(input.getCreatedAt());
        found.setExpiredAt(input.getExpiredAt());
    }

    @Override
    public void delete(String accessToken) {
        Optional<AuthToken> found = retrieve(accessToken);
        if (found.isPresent()) {
            storage.remove(accessToken);
            refreshTokenAccessTokenIndex.remove(found.get().getRefreshToken());
        }
    }
}
