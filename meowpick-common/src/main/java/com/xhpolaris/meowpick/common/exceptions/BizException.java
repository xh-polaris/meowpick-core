package com.xhpolaris.meowpick.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BizException extends RuntimeException{
    private final Integer code;
    private final String msg;

    public BizException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static final BizException NotFind = of(State.NOT_FIND);
    public static BizException NotFind() {
        return NotFind;
    }

    private static BizException of(State state) {
        return new BizException((int) (state.code * 1000), state.msg);
    }

    @Getter
    @AllArgsConstructor
    public enum State {
        NOT_FIND(4.404f, ""),
        ;
        private final Float code;
        private final String msg;
    }
}
