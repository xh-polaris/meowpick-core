package com.xhpolaris.meowpick.common.enums;

import com.xhpolaris.meowpick.common.consts.Consts;

public interface Enums {
    String getLabel();

    Integer getCode();

    default String getValue() {
        return String.format("%s%s%d", this.getClass().getSimpleName(), Consts.SPLIT, getCode());
    }
}
