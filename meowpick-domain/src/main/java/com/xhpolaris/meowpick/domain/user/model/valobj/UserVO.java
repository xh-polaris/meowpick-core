package com.xhpolaris.meowpick.domain.user.model.valobj;

import com.xhpolaris.meowpick.common.authorize.MeowUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "UserVO")
public class UserVO {
    private String  id;
    private String  name;
    private String  avatar;
    private boolean account_enable;
    private boolean account_lock;
    private boolean account_expire;
    private boolean account_credential;

    public static UserVO of(MeowUser user) {
        UserVO vo = new UserVO();

        vo.setId(user.getUserId());
        vo.setName(user.getDisplayName());
        vo.setAvatar(user.getAvatar());

        return vo;
    }
}
