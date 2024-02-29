package com.xhpolaris.meowpick.domain.user.auto_login;

import com.xhpolaris.meowpick.domain.user.model.entity.LoginCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.TokenVO;

public interface IAutoLogin {
    TokenVO autoLogin(LoginCmd.Query query);
}
