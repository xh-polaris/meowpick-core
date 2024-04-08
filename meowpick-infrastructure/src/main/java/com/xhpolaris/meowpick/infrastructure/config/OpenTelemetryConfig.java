package com.xhpolaris.meowpick.infrastructure.config;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenTelemetryConfig {

    @Bean
    OpenTelemetry getOpenTelemetry() {
        return GlobalOpenTelemetry.get();
    }
}
