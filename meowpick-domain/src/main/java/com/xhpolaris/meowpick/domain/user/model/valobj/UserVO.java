package com.xhpolaris.meowpick.domain.user.model.valobj;

import com.xhpolaris.meowpick.common.security.authorize.MeowUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "UserVO")
public class UserVO {
    private String id;
    private String name;

    public static UserVO of(MeowUser user) {
        UserVO vo = new UserVO();

        vo.setId(user.getUserId());
        vo.setName(user.getDisplayName());

        return vo;
    }
}
