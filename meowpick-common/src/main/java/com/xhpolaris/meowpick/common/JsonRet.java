package com.xhpolaris.meowpick.common;

import com.xhpolaris.meowpick.common.enums.HttpStateEn;
import com.xhpolaris.meowpick.common.exceptions.BizException;
import lombok.Data;

import java.util.Map;

@Data
public class JsonRet {
    @Data
    static class State {
        private Integer code;
        private String errMsg;
    }

    private State state;
    private Object payload;

    public static JsonRet fail(Integer code, String msg, Object ...args) {
        JsonRet data = new JsonRet();
        State type = new State();

        type.setCode(code);
        type.setErrMsg(String.format(msg, args));

        data.setState(type);

        data.setPayload(Map.of());
        return data;
    }

    public static JsonRet fail(HttpStateEn state) {
        return fail(state, state.getMsg());
    }

    public static JsonRet fail(HttpStateEn state, Object ...args) {
        return fail(state.getCode(), state.getMsg(), args);
    }

    public static JsonRet fail(BizException exception, Object ...args) {
        return fail(exception.getCode(), exception.getMsg(), args);
    }

    public static JsonRet then(Object payload) {
        JsonRet data = new JsonRet();
        State type = new State();

        type.setCode(0);
        type.setErrMsg("success");

        data.setState(type);
        data.setPayload(payload);

        return data;
    }
}
