package com.xhpolaris.meowpick.trigger.http.config;

import com.xhpolaris.meowpick.common.mvc.String2EnumConverterFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SuppressWarnings("all")
@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new String2EnumConverterFactory());
    }
}
