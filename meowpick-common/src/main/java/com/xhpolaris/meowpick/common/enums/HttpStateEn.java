package com.xhpolaris.meowpick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpStateEn implements BaseEnum {
    unauthorized("未授权", "un_authorized", 1404),
    un_login("未登录", "un_login", 1403),
    tar_not_exists("目标不存在", "not_exists", 1501);

    private final String msg;
    private final String value;
    private final Integer code;

}
