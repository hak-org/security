package org.hak.security.authentication;

import org.hak.security.authentication.entity.Authentication;

public class PasswordAuthentication implements Authentication {

    private String username;
    private String password;
    private String checkPassword;

    private PasswordAuthentication() {}

    @Override
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public static PasswordAuthentication signupForm(String username,
                                                    String password, String checkPassword) {
        PasswordAuthentication authentication = new PasswordAuthentication();
        authentication.username = username;
        authentication.password = password;
        authentication.checkPassword = checkPassword;
        return authentication;
    }

    public static PasswordAuthentication loginForm(String username, String password) {
        PasswordAuthentication authentication = new PasswordAuthentication();
        authentication.username = username;
        authentication.password = password;
        return authentication;
    }

    @Override
    public String toString() {
        return "PasswordAuthentication{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", checkPassword='" + checkPassword + '\'' +
                '}';
    }
}
