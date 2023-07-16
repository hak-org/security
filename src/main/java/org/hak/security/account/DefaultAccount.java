package org.hak.security.account;

import org.hak.security.account.entity.Account;

import java.time.LocalDateTime;

public class DefaultAccount implements Account {

    private String uid;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private DefaultAccount() {}

    @Override
    public String getUid() {
        return uid;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static DefaultAccount newInstance(String uid, String username, String password,
                                             LocalDateTime createdAt, LocalDateTime updatedAt) {
        DefaultAccount account = new DefaultAccount();
        account.uid = uid;
        account.username = username;
        account.password = password;
        account.createdAt = createdAt;
        account.updatedAt = updatedAt;
        return account;
    }

    public static DefaultAccount createForm(String username, String password) {
        DefaultAccount account = new DefaultAccount();
        account.username = username;
        account.password = password;
        account.createdAt = LocalDateTime.now();
        return account;
    }

    public static DefaultAccount updateForm(String uid) {
        DefaultAccount account = new DefaultAccount();
        account.uid = uid;
        account.updatedAt = LocalDateTime.now();
        return account;
    }

    @Override
    public String toString() {
        return "DefaultAccount{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
