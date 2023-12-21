package com.xhpolaris.meowpick.common;

import lombok.Data;

@Data
public class JsonRet {
    private Integer code;
    private String errMsg;
    private Object value;
}
