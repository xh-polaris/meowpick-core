package com.xhpolaris.meowpick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpStateEn implements BaseEnum {
    UNAUTHORIZED("未授权", "un_authorized",1404),
    TARGETNOTEXISTS("目标不存在", "not_exists",1501);

    private final String msg;
    private String value;
    private final Integer code;

}
