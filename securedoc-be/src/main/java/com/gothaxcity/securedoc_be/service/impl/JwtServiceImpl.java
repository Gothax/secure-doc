package com.gothaxcity.securedoc_be.service.impl;


import com.gothaxcity.securedoc_be.domain.Token;
import com.gothaxcity.securedoc_be.domain.TokenData;
import com.gothaxcity.securedoc_be.dto.UserDto;
import com.gothaxcity.securedoc_be.enumeration.TokenType;
import com.gothaxcity.securedoc_be.security.JwtConfiguration;
import com.gothaxcity.securedoc_be.service.JwtService;
import com.gothaxcity.securedoc_be.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.gothaxcity.securedoc_be.constant.Constants.*;
import static java.util.Arrays.stream;
import static java.util.Optional.empty;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl extends JwtConfiguration implements JwtService {

    private final UserService userService;

    private final Supplier<SecretKey> key = () -> Keys.hmacShaKeyFor(Decoders.BASE64.decode(getSecret()));

    private final Function<String, Claims> claimsFunction = token ->
            Jwts.parser().verifyWith(key.get())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

    private final Function<String, String> subject = token ->
            getClaimsValue(token, Claims::getSubject);

    private final BiFunction<HttpServletRequest, String, Optional<String>> extractToken = (request, cookieName) ->
            Optional.of(stream(request.getCookies() == null ? new Cookie[]{new Cookie(EMPTY_VALUE, EMPTY_VALUE)} : stream(request.getCookies())
                    .filter(cookie -> Objects.equals(cookieName, cookie.getName()))
                    .map(Cookie::getValue)
                    .findAny()))
                    .orElse(empty());

    public Function<String, List<GrantedAuthority>> authorities = token ->
            commaSeperatedStringToAuthorityList(new StringJoiner(AUTHORITY_DELIMITER)
                                                        .add(claimsFunction.apply(token).get(AUTHORITIES, String.class))
                                                        .add(ROLE_PREFIX + claimsFunction.apply(token).get(ROLE, String.class)).toString());

    @Override
    public String createToken(UserDto user, Function<Token, String> tokenFunction) {
        return "";
    }

    @Override
    public Optional<String> extractToken(HttpServletRequest request, String tokenType) {
        return empty();
    }

    @Override
    public void addCookie(HttpServletResponse response, UserDto user, TokenType type) {

    }

    @Override
    public <T> T getTokenData(String token, Function<TokenData, T> tokenFunction) {
        return null;
    }
}
