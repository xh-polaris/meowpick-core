package com.xhpolaris.meowpick.domain.user.model.valobj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class TokenVO {
    private String token;
    private UserVO user;

    public static TokenVO of(UserVO user) {
        TokenVO vo = new TokenVO();
        vo.setUser(user);
        return vo;
    }
}
