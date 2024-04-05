package com.xhpolaris.meowpick.domain.service.user.auto_login;

import com.xhpolaris.meowpick.domain.model.valobj.LoginCmd;
import com.xhpolaris.meowpick.domain.model.valobj.UserVO;

public interface IAutoLogin {
    UserVO autoLogin(LoginCmd.Query query);
}
