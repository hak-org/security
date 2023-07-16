package org.hak.security.account;

import org.hak.security.account.entity.Account;
import org.hak.security.account.entity.Accounts;

import java.util.NoSuchElementException;
import java.util.Optional;

public class DefaultAccountService implements AccountService {

    private final Accounts accounts;

    public DefaultAccountService(Accounts accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean exists(String uid) {
        return accounts.exists(uid);
    }

    @Override
    public Account create(Account form) {

        if (accounts.retrieveByUsername(form.getUsername()).isPresent()) {
            throw new AccountAlreadyExistException();
        }

        return accounts.create(form);
    }

    @Override
    public Optional<Account> retrieve(String uid) {
        return accounts.retrieve(uid);
    }

    @Override
    public Optional<Account> retrieveByUsername(String username) {
        return accounts.retrieveByUsername(username);
    }

    @Override
    public void update(Account form) {
        if (!exists(form.getUid())) {
            throw new NoSuchElementException();
        }
        accounts.update(form);
    }

    @Override
    public void delete(String uid) {
        if (!exists(uid)) {
            throw new NoSuchElementException();
        }
        accounts.delete(uid);
    }
}
