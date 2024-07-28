package com.gothaxcity.securedoc_be.domain;

import com.gothaxcity.securedoc_be.dto.UserDto;
import com.gothaxcity.securedoc_be.exception.ApiException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class ApiAuthentication extends AbstractAuthenticationToken {
    private UserDto user;
    private String email;
    private String password;
    private boolean authenticated;
    private static final String PASSWORD_PROTECTED = "[PASSWORD PROTECTED]";
    private static final String EMAIL_PROTECTED = "[EMAIL PROTECTED]";

    @Override
    public void setAuthenticated(boolean authenticated){
        throw new ApiException("You cannot set authentication");
    }

    @Override
    public boolean isAuthenticated() {
        return this.isAuthenticated();
    }

    private ApiAuthentication(UserDto user, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = user;
        this.password = PASSWORD_PROTECTED;
        this.email = EMAIL_PROTECTED;
        this.authenticated = true;
    }

    private ApiAuthentication(String email, String password) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.password = PASSWORD_PROTECTED;
        this.email = EMAIL_PROTECTED;
        this.authenticated = false;
    }

    public static ApiAuthentication authenticated(UserDto user, Collection<? extends GrantedAuthority> authorities) {
        return new ApiAuthentication(user, authorities);
    }

    public static ApiAuthentication unauthenticated(String email, String password) {
        return new ApiAuthentication(email, password);
    }

    @Override
    public Object getCredentials() {
        return PASSWORD_PROTECTED;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    public String getPassword(){
        return this.password;
    }

    public String getEmail(){
        return this.email;
    }
}
