package com.gothaxcity.securedoc_be.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean(name = {"userLoginCache"})
    public CacheStore<String, Integer> userCache() {
        // 15분
        return new CacheStore<>(900, TimeUnit.SECONDS);
    }

}
