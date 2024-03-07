package com.xhpolaris.meowpick.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "weapp")
public class WeappProperties {
    private String appid;
    private String secret;
    private String url;
}
