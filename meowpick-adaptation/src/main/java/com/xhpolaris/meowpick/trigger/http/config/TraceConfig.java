package com.xhpolaris.meowpick.trigger.http.config;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TraceConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceInterceptor())
                .addPathPatterns("/api/**")  // 拦截的路径
                .excludePathPatterns("/public/**");  // 排除的路径
    }
}

class TraceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Span currentSpan = Span.fromContext(Context.current());
        // 获取当前请求的Trace ID
        String traceId = currentSpan.getSpanContext().getTraceId();
        response.addHeader("X-Trace-Id", traceId);
        return true;
    }
}