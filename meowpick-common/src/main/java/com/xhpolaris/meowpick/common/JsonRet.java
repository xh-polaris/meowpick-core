package com.xhpolaris.meowpick.common;

import com.xhpolaris.meowpick.common.enums.HttpStateEn;
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

    public static JsonRet fail(HttpStateEn state) {
        return fail(state, state.getMsg());
    }

    public static JsonRet fail(HttpStateEn state, Object ...args) {
        JsonRet data = new JsonRet();

        State type = new State();

        type.setCode(state.getCode());
        type.setErrMsg(String.format(state.getMsg(), args));

        data.setState(type);

        data.setPayload(Map.of());

        return data;
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
