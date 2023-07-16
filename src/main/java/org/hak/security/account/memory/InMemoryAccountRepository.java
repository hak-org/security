package org.hak.security.account.memory;

import org.hak.security.account.DefaultAccount;
import org.hak.security.account.entity.Account;
import org.hak.security.account.entity.Accounts;

import java.util.*;

public class InMemoryAccountRepository implements Accounts {

    private final Map<String, InMemoryAccount> storage;
    private final Map<String, String> uidUsernameIndex;

    public InMemoryAccountRepository(Map<String, InMemoryAccount> storage) {
        this.storage = storage;
        this.uidUsernameIndex = new HashMap<>();
    }

    @Override
    public boolean exists(String uid) {
        return storage.containsKey(uid);
    }

    @Override
    public Account create(Account input) {
        String uid = generateUid();
        InMemoryAccount entity = new InMemoryAccount(
                uid, input.getUsername(), input.getPassword(),
                input.getCreatedAt(), input.getUpdatedAt());
        storage.put(uid, entity);
        uidUsernameIndex.put(input.getUsername(), uid);
        return DefaultAccount.newInstance(
                uid, entity.getUsername(), entity.getPassword(),
                entity.getCreatedAt(), entity.getUpdatedAt());
    }

    @Override
    public Optional<Account> retrieve(String uid) {
        InMemoryAccount found = storage.get(uid);
        if (found == null) {
            return Optional.empty();
        }
        Account user = DefaultAccount.newInstance(
                found.getUid(), found.getUsername(), found.getPassword(),
                found.getCreatedAt(), found.getUpdatedAt());
        return Optional.of(user);
    }

    @Override
    public Optional<Account> retrieveByUsername(String username) {
        String uid = uidUsernameIndex.get(username);
        return retrieve(uid);
    }

    @Override
    public void update(Account input) {
        String uid = input.getUid();
        InMemoryAccount found = storage.get(uid);
        if (found == null) {
            throw new NoSuchElementException();
        }
        found.setUpdatedAt(input.getUpdatedAt());
        found.setUsername(input.getUsername());
    }

    @Override
    public void delete(String uid) {
        if (!exists(uid)) {
            throw new NoSuchElementException();
        }
        String username = storage.get(uid).getUsername();
        storage.remove(uid);
        uidUsernameIndex.remove(username);
    }

    protected String generateUid() {
        return UUID.randomUUID().toString();
    }
}
