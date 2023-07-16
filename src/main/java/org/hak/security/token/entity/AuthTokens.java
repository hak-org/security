package org.hak.security.token.entity;

import org.hak.core.data.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface AuthTokens extends DomainRepository<String, AuthToken> {

    Optional<AuthToken> retrieveByRefreshToken(String refreshToken);

    List<? extends AuthToken> retrieveAllByUid(String uid);

}
