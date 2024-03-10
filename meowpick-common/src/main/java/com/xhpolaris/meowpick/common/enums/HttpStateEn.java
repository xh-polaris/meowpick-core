package com.xhpolaris.meowpick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpStateEn implements BaseEnum {
    unauthorized("未授权", "un_authorized", 4031),
    un_login("未登录", "un_login", 4032),
    tar_not_exists("目标不存在", "not_exists", 4041),
    biz("%s", "",5001)
    ;

    private final String  msg;
    private final String  value;
    private final Integer code;

}
