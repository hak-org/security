package org.hak.security.account.entity;

import org.hak.core.data.DomainRepository;

import java.util.Optional;

public interface Accounts extends DomainRepository<String, Account> {

    Optional<Account> retrieveByUsername(String username);

}
