package com.xhpolaris.meowpick.config;

import com.xhpolaris.meowpick.common.properties.DocInfoProperties;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "doc-info.enable", havingValue = "true")
public class SwaggerConfig {
    private final DocInfoProperties docInfo;

    @Bean
    public OpenAPI heroOpenAPI() {
        return new OpenAPI().info(new Info().title(docInfo.getTitle())
                                            .description(docInfo.getDescription())
                                            .version(docInfo.getVersion()))
                            .externalDocs(new ExternalDocumentation().description(docInfo.getDescription())
                                                                     .url(docInfo.getWebsiteUrl()));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                             .group(docInfo.getDescription())
                             .pathsToMatch("/api/**")
                             .build();
    }
}
