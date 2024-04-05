package com.xhpolaris.meowpick.domain.service.login;

import com.xhpolaris.meowpick.domain.model.valobj.LoginCmd;
import com.xhpolaris.meowpick.domain.model.valobj.UserVO;

public interface IUserLogin {
//    账号密码登录
    default UserVO usernamePassword(LoginCmd.Query query) {
        throw new UnsupportedOperationException("");
    }

//    邮箱登录
    default UserVO email(LoginCmd.Query query) {
        throw new UnsupportedOperationException("");
    }

//    手机验证码登录
    default UserVO phone(LoginCmd.Query query) {
        throw new UnsupportedOperationException("");
    }
    default UserVO meowchat(LoginCmd.Query query) {
        throw new UnsupportedOperationException("");
    }
}
