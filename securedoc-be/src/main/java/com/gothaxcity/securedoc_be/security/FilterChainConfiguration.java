package com.gothaxcity.securedoc_be.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

import static com.gothaxcity.securedoc_be.constant.Constants.LOGIN_PATH;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class FilterChainConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                                                  request.requestMatchers(LOGIN_PATH).permitAll()
                                                          .anyRequest().authenticated())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        ApiAuthenticationProvider apiAuthenticationProvider = new ApiAuthenticationProvider(userDetailsService);
        return new ProviderManager(apiAuthenticationProvider);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails exUser1 = User.withUsername("user1").password("password").roles("USER").build();
        UserDetails exUser2 = User.withUsername("user2").password("password").roles("USER").build();
        return new InMemoryUserDetailsManager(List.of(exUser1, exUser2));
    }

    @Bean
    public UserDetailsService inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password("password").roles("USER").build(),
                User.withUsername("user2").password("password").roles("USER").build()
        );
    }

}
