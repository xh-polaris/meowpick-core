package com.xhpolaris.meowpick.common.enums.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentStats {
    SHOW("展示", 0),
    HIDDEN("隐藏",1)
    ;
    private final String label;
    private final Integer code;
}
