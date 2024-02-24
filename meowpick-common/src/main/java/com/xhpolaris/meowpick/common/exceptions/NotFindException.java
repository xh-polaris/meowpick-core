package com.xhpolaris.meowpick.common.exceptions;

public class NotFindException extends RuntimeException{
    public NotFindException() {
    }

    public NotFindException(String message) {
        super(message);
    }
}
