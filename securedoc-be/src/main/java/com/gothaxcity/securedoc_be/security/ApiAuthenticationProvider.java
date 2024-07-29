package com.gothaxcity.securedoc_be.security;

import com.gothaxcity.securedoc_be.domain.ApiAuthentication;
import com.gothaxcity.securedoc_be.domain.UserPrincipal;
import com.gothaxcity.securedoc_be.dto.UserDto;
import com.gothaxcity.securedoc_be.entity.CredentialEntity;
import com.gothaxcity.securedoc_be.exception.ApiException;
import com.gothaxcity.securedoc_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ApiAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        var user = (UsernamePasswordAuthenticationToken) authentication;
//        var userFromDb = userDetailsService.loadUserByUsername(user.getPrincipal().toString());
//        if( user.getCredentials().equals(userFromDb.getPassword()) ) {
//            return UsernamePasswordAuthenticationToken.authenticated(userFromDb, "[PASSWORD PROTECTED]", null);
//        }
//        throw new BadCredentialsException("Unable to login.");
//    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ApiAuthentication apiAuthentication = authenticationFunction.apply(authentication);
        UserDto user = userService.getUserByEmail(apiAuthentication.getEmail());
        if (user != null) {
            CredentialEntity userCredential = userService.getUserCredentialById(user.getId());
            if (userCredential.getUpdatedAt().minusDays(90).isAfter(LocalDateTime.now())) {throw new ApiException("Credential expired. Please reset your password"); }

            new UserPrincipal(user, userCredential);
        } else throw new ApiException("Unable to authenticate user");
    }

    private final Function<Authentication, ApiAuthentication> authenticationFunction = authentication -> (ApiAuthentication) authentication;

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private final Consumer<UserPrincipal> validAccount = userPrincipal -> {
        if (userPrincipal.isAccountNonLocked()) {
            throw new LockedException("Your account is currently locked);
        }
    };
}
