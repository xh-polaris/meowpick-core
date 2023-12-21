package com.xhpolaris.meowpick.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "doc-info")
public class DocInfoProperties {
    private String title;
    private String description;
    private String version;
    private String websiteName;
    private String websiteUrl;
    private boolean enable;
}