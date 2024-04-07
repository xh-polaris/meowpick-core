package com.xhpolaris.meowpick.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class BizException extends RuntimeException{
    private final Integer code;
    private String msg;

    public BizException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static final BizException NotFind = of(State.NOT_FIND);
    public static BizException NotFind(Object ...args) {
        return of(NotFind, args);
    }

    public static final BizException UnsupportedOperation = of(State.UNSUPPORTED_OPERATION);
    public static BizException UnsupportedOperation(Object ...args) {
        return of(UnsupportedOperation, args);
    }

    public static final BizException IllegalArgument = of(State.ILLEGAL_ARGUMENT);
    public static BizException IllegalArgument(Object ...args) {
        return of(IllegalArgument, args);
    }

    private static BizException of(State state) {
        return new BizException((int) (state.code * 1000), state.msg);
    }
    private static BizException of(BizException exception, Object ...args) {
        exception.msg = String.format("%s: [ %s ]", exception.msg, Arrays.toString(args));
        return exception;
    }

    public void Throw() {
        throw this;
    }

    @Getter
    @AllArgsConstructor
    public enum State {
        ILLEGAL_ARGUMENT(4.401f, "IllegalArgument"),
        NOT_FIND(4.404f, ""),
        UNSUPPORTED_OPERATION(5.501f, "UnsupportedOperation"),

        ;
        private final Float code;
        private final String msg;
    }
}
