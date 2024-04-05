package com.xhpolaris.meowpick.domain.service.user.auto_login.impl;

import com.xhpolaris.meowpick.common.enums.UserLoginEn;
import com.xhpolaris.meowpick.domain.service.user.auto_login.AbstractAutoLogin;
import com.xhpolaris.meowpick.domain.model.valobj.LoginCmd;
import com.xhpolaris.meowpick.domain.model.valobj.UserVO;
import org.springframework.stereotype.Component;

@Component(UserLoginEn.Name.meowchat)
public class MeowChatAutoLogin extends AbstractAutoLogin {
    @Override
    public UserVO meowchat(LoginCmd.Query query) {
        LoginCmd.CreateCmd cmd = new LoginCmd.CreateCmd();
        cmd.setId(query.getToken());
        return userRepository.registry(cmd);
    }
}
