package com.xhpolaris.meowpick.common.exceptions;

public class BizException extends RuntimeException{
    public BizException(String message) {
        super(message);
    }

    public BizException() {
    }
}
