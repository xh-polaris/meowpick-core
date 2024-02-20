package com.xhpolaris.meowpick.config;

import com.xhpolaris.meowpick.common.enums.BaseEnum;
import com.xhpolaris.meowpick.common.mvc.String2EnumConverterFactory;

import org.bson.json.StrictJsonWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

@Configuration
@EnableWebMvc
@SuppressWarnings("all")
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new String2EnumConverterFactory());
    }
}
