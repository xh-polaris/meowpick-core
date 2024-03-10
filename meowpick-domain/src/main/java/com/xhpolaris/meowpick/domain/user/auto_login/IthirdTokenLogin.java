package com.xhpolaris.meowpick.domain.user.auto_login;

import com.xhpolaris.meowpick.domain.user.model.entity.LoginCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.UserVO;

public interface IthirdTokenLogin {

    //    第三方登录
    default UserVO third(LoginCmd.Query query) {
        throw new UnsupportedOperationException("");
    }
}
