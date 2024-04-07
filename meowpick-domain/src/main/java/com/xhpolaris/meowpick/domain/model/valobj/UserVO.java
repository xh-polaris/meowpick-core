package com.xhpolaris.meowpick.domain.model.valobj;

import com.xhpolaris.meowpick.domain.model.entity.MeowUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "UserVO")
public class UserVO {
    private String  id;
    private String  name;
    private String  avatar;
    private boolean enable;
    private boolean lock;
    private boolean expire;
    private boolean credential;

    public static UserVO of(MeowUser user) {
        UserVO vo = new UserVO();

        vo.setId(user.getUserId());
        vo.setName(user.getDisplayName());
        vo.setAvatar(user.getAvatar());

        return vo;
    }
}
