package org.hak.security.account.entity;

import java.time.LocalDateTime;

public interface Account {

    String getUid();
    String getUsername();
    String getPassword();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();

}
