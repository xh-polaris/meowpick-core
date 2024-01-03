package com.xhpolaris.meowpick.common.enums.state;

import com.xhpolaris.meowpick.common.enums.EnumValue;
import com.xhpolaris.meowpick.common.enums.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@EnumValue("评论状态")
@AllArgsConstructor
public enum CommentStats implements Enums {
    SHOW("展示", 0),
    HIDDEN("隐藏",1)
    ;
    private final String label;
    private final Integer code;
}
