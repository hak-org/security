package org.hak.security.account;

import org.hak.security.account.entity.Account;

import java.util.Optional;

public interface AccountService {

    boolean exists(String uid);

    Account create(Account input);

    Optional<Account> retrieve(String uid);

    Optional<Account> retrieveByUsername(String username);

    void update(Account input);

    void delete(String uid);

}
