package com.xhpolaris.meowpick.domain.user.auto_login;

import com.xhpolaris.meowpick.domain.user.model.entity.LoginCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.TokenVO;

public interface ILogin {
    default TokenVO phone(LoginCmd.Query query) {
        throw new UnsupportedOperationException("");
    }

    default TokenVO weapp(LoginCmd.Query query) {
        throw new UnsupportedOperationException("");
    }

    default TokenVO wx(LoginCmd.Query query) {
        throw new UnsupportedOperationException("");
    }

    default TokenVO qq(LoginCmd.Query query) {
        throw new UnsupportedOperationException("");
    }

    default TokenVO tiktok(LoginCmd.Query query) {
        throw new UnsupportedOperationException("");
    }

    default TokenVO weibo(LoginCmd.Query query) {
        throw new UnsupportedOperationException("");
    }

    default TokenVO apple(LoginCmd.Query query) {
        throw new UnsupportedOperationException("");
    }

    default TokenVO google(LoginCmd.Query query) {
        throw new UnsupportedOperationException("");
    }

    default TokenVO facebook(LoginCmd.Query query) {
        throw new UnsupportedOperationException("");
    }

    default TokenVO twitter(LoginCmd.Query query) {
        throw new UnsupportedOperationException("");
    }
}
