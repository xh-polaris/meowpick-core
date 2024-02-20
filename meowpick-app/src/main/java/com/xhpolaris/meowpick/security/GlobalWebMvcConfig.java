//package com.xhpolaris.meowpick.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@RequiredArgsConstructor
//@ConditionalOnProperty(name = "app.security.enable", havingValue = "true")
//public class GlobalWebMvcConfig implements WebMvcConfigurer {
//    private final HandlerInterceptor interceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(interceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/health")
//                .excludePathPatterns("/doc.html#/home")
//                .excludePathPatterns("/api/user/**");
//    }
//}
