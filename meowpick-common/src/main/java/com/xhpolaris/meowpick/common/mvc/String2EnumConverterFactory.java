package com.xhpolaris.meowpick.common.mvc;

import com.xhpolaris.meowpick.common.enums.BaseEnum;
import com.xhpolaris.meowpick.common.enums.CommentStatsEn;
import com.xhpolaris.meowpick.common.enums.HttpStateEn;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class String2EnumConverterFactory implements ConverterFactory<String, BaseEnum> {
    private static Map<Class, Converter> converterMaps = new HashMap<>(16);

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return converterMaps.getOrDefault(targetType, new Converter<String, T>() {
            @Override
            public T convert(String source) {
                T[] enums = targetType.getEnumConstants();
                for (T en : enums) {
                    if (en.getCode().toString().equals(source) || en.getValue().equals(source)) {
                        return en;
                    }
                }

                throw new IllegalArgumentException(String.format("Enums: %s not match",source));
            }
        });
    }
}
