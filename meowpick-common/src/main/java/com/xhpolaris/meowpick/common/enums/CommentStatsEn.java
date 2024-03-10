package com.xhpolaris.meowpick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentStatsEn implements BaseEnum {
    SHOW("展示", "show",0),
    HIDDEN("隐藏","hidden",1)
    ;
    private final String msg;
    private final String value;
    private final Integer code;
}
