package com.xhpolaris.meowpick.config;

import com.google.gson.Gson;
import com.xhpolaris.meowpick.common.JsonRet;
import com.xhpolaris.meowpick.common.enums.HttpStateEn;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@SuppressWarnings("all")
@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "com.xhpolaris.meowpick.trigger.http")
public class WebWrapper implements ResponseBodyAdvice<Object> {
    private final Gson gson;

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof HttpStateEn state) {
            return JsonRet.fail(state);
        }
        if (body instanceof String) {
            return gson.toJson(JsonRet.then(body));
        }
        if (body instanceof JsonRet) {
            return body;
        }
        return JsonRet.then(body);
    }
}
