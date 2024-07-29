package com.gothaxcity.securedoc_be.domain;

import com.gothaxcity.securedoc_be.dto.UserDto;
import com.gothaxcity.securedoc_be.entity.CredentialEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

    private final UserDto user;
    private final CredentialEntity credentialEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
