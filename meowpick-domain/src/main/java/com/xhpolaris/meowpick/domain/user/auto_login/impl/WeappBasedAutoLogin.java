package com.xhpolaris.meowpick.domain.user.auto_login.impl;

import com.xhpolaris.meowpick.common.enums.UserLoginEn;
import com.xhpolaris.meowpick.domain.user.auto_login.AbstractAutoLogin;
import com.xhpolaris.meowpick.domain.user.model.entity.LoginCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.TokenVO;
import com.xhpolaris.meowpick.domain.user.model.valobj.UserVO;
import org.springframework.stereotype.Component;

@Component(UserLoginEn.Name.weapp)
public class WeappBasedAutoLogin extends AbstractAutoLogin {
    @Override
    protected void registryInfo(LoginCmd.Query query, LoginCmd.CreateCmd cmd) {

    }

    @Override
    protected UserVO getUser(LoginCmd.Query query) {
        return null;
    }

    @Override
    public TokenVO weapp(LoginCmd.Query query) {
        return super.weapp(query);
    }
}
