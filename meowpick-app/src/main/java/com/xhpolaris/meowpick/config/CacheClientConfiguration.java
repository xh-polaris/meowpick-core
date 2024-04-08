package com.xhpolaris.meowpick.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class CacheClientConfiguration {

    @Bean
    public Cache<String, Object> local() {
        return Caffeine.newBuilder()
                       .expireAfterWrite(5, TimeUnit.MINUTES)
                       .initialCapacity(1000)
                       .maximumSize(1000)
                       .build();
    }
}
