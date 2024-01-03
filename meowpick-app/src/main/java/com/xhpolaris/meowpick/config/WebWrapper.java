package com.xhpolaris.meowpick.config;

import com.google.gson.Gson;
import com.xhpolaris.meowpick.common.JsonRet;
import com.xhpolaris.meowpick.common.enums.state.HttpState;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "com.xhpolaris.meowpick.trigger.http")
public class WebWrapper implements ResponseBodyAdvice<Object> {
    private final Gson gson;

    @Override
    public boolean supports(@NonNull MethodParameter returnType,
                            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {
        if (body instanceof HttpState state) {
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
