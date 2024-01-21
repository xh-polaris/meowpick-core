package com.xhpolaris.meowpick.cache;

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
    public CacheContext cacheContext() {
        return new CacheContext();
    }

    @Bean
    public Cache<String, Object> local() {
        return Caffeine.newBuilder()
                       .expireAfterWrite(5, TimeUnit.MINUTES)
                       .initialCapacity(1000)
                       .maximumSize(1000)
                       .build();
    }
}
