package com.gothaxcity.securedoc_be.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter @Setter
public class JwtConfiguration {

    @Value("${jwt.expriation}")
    private Long expiration;
    @Value("${jwt.secret}")
    private String secret;
}
