package com.xhpolaris.meowpick.trigger.http.config;

import com.xhpolaris.meowpick.common.JsonRet;
import com.xhpolaris.meowpick.common.enums.HttpStateEn;
import com.xhpolaris.meowpick.common.exceptions.BizException;
import com.xhpolaris.meowpick.common.utils.Meowpick;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("all")
@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "com.xhpolaris.meowpick.trigger.http")
public class WebWrapper implements ResponseBodyAdvice<Object> {

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
            return Meowpick.toJson(JsonRet.then(body));
        }
        if (body instanceof JsonRet) {
            return body;
        }
        return JsonRet.then(body);
    }
}
