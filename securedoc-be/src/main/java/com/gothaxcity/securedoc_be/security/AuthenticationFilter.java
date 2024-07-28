package com.gothaxcity.securedoc_be.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gothaxcity.securedoc_be.domain.ApiAuthentication;
import com.gothaxcity.securedoc_be.dtorequest.LoginRequest;
import com.gothaxcity.securedoc_be.enumeration.LoginType;
import com.gothaxcity.securedoc_be.service.JwtService;
import com.gothaxcity.securedoc_be.service.UserService;
import com.gothaxcity.securedoc_be.utils.RequestUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

import static com.gothaxcity.securedoc_be.domain.ApiAuthentication.unauthenticated;
import static com.gothaxcity.securedoc_be.utils.RequestUtils.handleErrorResponse;
import static org.springframework.http.HttpMethod.POST;

@Slf4j
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final UserService userService;
    private final JwtService jwtService;

    protected AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        super(new AntPathRequestMatcher("/user/login", POST.name()), authenticationManager);
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        try {
            LoginRequest user = new ObjectMapper().configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true).readValue(request.getInputStream(), LoginRequest.class);
            userService.updateLoginAttempt(user.getEmail(), LoginType.LOGIN_ATTEMPT);
            ApiAuthentication authentication = unauthenticated(user.getEmail(), user.getPassword());
            return getAuthenticationManager().authenticate(authentication);
        } catch (Exception e) {
            log.error(e.getMessage());
            handleErrorResponse(request, response, e);
            return null;
        }
    }


}
