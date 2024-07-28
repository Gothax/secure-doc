package com.gothaxcity.securedoc_be.service;


import com.gothaxcity.securedoc_be.domain.Token;
import com.gothaxcity.securedoc_be.domain.TokenData;
import com.gothaxcity.securedoc_be.dto.UserDto;
import com.gothaxcity.securedoc_be.enumeration.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;
import java.util.function.Function;

public interface JwtService {
    String createToken(UserDto user, Function<Token, String> tokenFunction);
    Optional<String> extractToken(HttpServletRequest request, String tokenType);
    void addCookie(HttpServletResponse response, UserDto user, TokenType type);
    <T> T getTokenData(String token, Function<TokenData, T> tokenFunction);
}
