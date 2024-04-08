package com.xhpolaris.meowpick.domain.model.valobj;

import lombok.Data;

@Data
public class WeappVO {
    private String key;
    private String openid;

    @Data
    public static class WxResponse {
        private String unionid;
    }
}
