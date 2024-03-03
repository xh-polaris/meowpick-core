package com.xhpolaris.meowpick.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "search-history")
public class SearchHistoryProperties {
    private Integer size = 8;
}
