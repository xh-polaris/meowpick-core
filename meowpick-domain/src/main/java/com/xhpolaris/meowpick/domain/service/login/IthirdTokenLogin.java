package com.xhpolaris.meowpick.domain.service.login;

import com.xhpolaris.meowpick.common.exceptions.BizException;
import com.xhpolaris.meowpick.domain.model.valobj.LoginCmd;
import com.xhpolaris.meowpick.domain.model.valobj.UserVO;

public interface IthirdTokenLogin {

    //    第三方登录
    default UserVO third(LoginCmd.Query query) {
        throw BizException.UnsupportedOperation;
    }
}
