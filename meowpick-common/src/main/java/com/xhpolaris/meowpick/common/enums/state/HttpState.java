package com.xhpolaris.meowpick.common.enums.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpState {
    UNAUTHORIZED("未授权", 1404), TARGETNOTEXISTS("目标不存在", 1501);

    private String msg;
    private final Integer code;

    public HttpState format(String msg) {
        this.msg = String.format("%s: [%s]", this.msg, msg);
        return this;
    }
}
