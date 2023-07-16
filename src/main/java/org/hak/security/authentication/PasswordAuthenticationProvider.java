package org.hak.security.authentication;

import org.hak.security.account.AccountService;
import org.hak.security.account.DefaultAccount;
import org.hak.security.account.UsernameNotFoundException;
import org.hak.security.account.entity.Account;
import org.hak.security.authentication.entity.Authentication;
import org.hak.security.authentication.entity.Me;
import org.hak.security.token.AuthTokenService;
import org.hak.security.token.DefaultAuthToken;
import org.hak.security.token.entity.AuthToken;

import java.util.NoSuchElementException;

public class PasswordAuthenticationProvider implements AuthenticationProvider {

    private final AccountService accountService;
    private final AuthTokenService tokenService;

    public PasswordAuthenticationProvider(AccountService accountService, AuthTokenService tokenService) {
        this.accountService = accountService;
        this.tokenService = tokenService;
    }

    @Override
    public Account signup(Authentication authentication) {

        PasswordAuthentication form = (PasswordAuthentication) authentication;

        //1. 패스워드 확인
        if (!form.getPassword().equals(form.getCheckPassword())) {
            throw new InvalidCheckPasswordException();
        }

        //2. 계정 추가
        return accountService.create(DefaultAccount.createForm(
                form.getUsername(), form.getPassword()));
    }

    @Override
    public AuthToken login(Authentication authentication) {

        PasswordAuthentication form = (PasswordAuthentication) authentication;

        //1. 사용자 존재확인
        Account account = accountService.retrieveByUsername(form.getUsername())
                .orElseThrow(UsernameNotFoundException::new);

        //2. 패스워드 확인
        if (!account.getPassword().equals(form.getPassword())) {
            throw new InvalidPasswordException();
        }

        //3. 토큰 발급
        return tokenService.generate(DefaultAuthToken.generateForm(account.getUid()));
    }

    @Override
    public void logout(String accessToken) {
        tokenService.revoke(accessToken);
    }

    @Override
    public void checkToken(String accessToken) {
        tokenService.verify(accessToken);
    }

    @Override
    public AuthToken refreshToken(String refreshToken) {
        return tokenService.refresh(refreshToken);
    }

    @Override
    public Me me(String accessToken) {
        String uid = tokenService.verify(accessToken);
        Account account = accountService.retrieve(uid).orElseThrow(NoSuchElementException::new);
        return DefaultMe.newInstance(account);
    }
}
