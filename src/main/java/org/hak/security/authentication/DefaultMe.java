package org.hak.security.authentication;

import org.hak.security.account.entity.Account;
import org.hak.security.authentication.entity.Me;

public class DefaultMe implements Me {

    private Account account;

    private DefaultMe() {}

    @Override
    public Account getMe() {
        return account;
    }

    public static DefaultMe newInstance(Account account) {
        DefaultMe me = new DefaultMe();
        me.account = account;
        return me;
    }

    @Override
    public String toString() {
        return "DefaultMe{" +
                "account=" + account +
                '}';
    }
}
