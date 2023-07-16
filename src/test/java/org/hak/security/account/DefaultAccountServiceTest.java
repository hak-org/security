package org.hak.security.account;

import org.hak.security.account.entity.Account;
import org.hak.security.account.entity.Accounts;
import org.hak.security.account.memory.InMemoryAccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DefaultAccountServiceTest {

    static DefaultAccountService service;

    @BeforeAll
    static void setup() {
        Accounts accounts = new InMemoryAccountRepository(new HashMap<>());
        service = new DefaultAccountService(accounts);
    }

    @Test
    void create() {
        DefaultAccount given = DefaultAccount.createForm("hakchangs", "1234");
        Account actual = service.create(given);
        System.out.println(actual);
        assertNotNull(actual);
        assertFalse(actual.getUid().isEmpty());
        assertEquals(given.getUsername(), actual.getUsername());
    }

    @Test
    void retrieveByUsername() {
        DefaultAccount sample = DefaultAccount.createForm("hakchangs", "1234");
        Account given = service.create(sample);
        Optional<Account> actual = service.retrieveByUsername(sample.getUsername());
        System.out.println(actual);
        assertFalse(actual.isEmpty());
        assertEquals(given.getUid(), actual.get().getUid());
    }

    @Test
    void update() {
        DefaultAccount sample = DefaultAccount.createForm("hakchangs", "1234");
        Account given = service.create(sample);
        System.out.println(given);
        final String uid = given.getUid();
        DefaultAccount when = DefaultAccount.updateForm(uid);
        service.update(when);
        Optional<Account> actual = service.retrieve(uid);
        System.out.println(actual);
        assertFalse(actual.isEmpty());
        assertEquals(uid, actual.get().getUid());
        assertNotEquals(given.getUpdatedAt(), actual.get().getUpdatedAt());
    }

    @Test
    void delete() {
        DefaultAccount sample = DefaultAccount.createForm("hakchangs", "1234");
        Account given = service.create(sample);
        final String uid = given.getUid();
        service.delete(uid);
        Optional<Account> actual = service.retrieve(uid);
        System.out.println(actual);
        assertTrue(actual.isEmpty());
    }
}