package com.xhpolaris.meowpick.domain.user.auto_login;

import com.xhpolaris.meowpick.domain.user.model.entity.LoginCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.UserVO;

public interface IAutoLogin {
    UserVO autoLogin(LoginCmd.Query query);
}
