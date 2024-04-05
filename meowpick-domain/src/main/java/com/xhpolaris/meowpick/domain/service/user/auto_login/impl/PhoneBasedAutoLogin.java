package com.xhpolaris.meowpick.domain.service.user.auto_login.impl;

import com.xhpolaris.meowpick.common.enums.UserLoginEn;
import com.xhpolaris.meowpick.domain.service.user.auto_login.AbstractAutoLogin;
import com.xhpolaris.meowpick.domain.model.valobj.LoginCmd;
import com.xhpolaris.meowpick.domain.model.valobj.UserVO;
import org.springframework.stereotype.Component;

@Component(UserLoginEn.Name.phone)
public class PhoneBasedAutoLogin extends AbstractAutoLogin {
    @Override
    public UserVO phone(LoginCmd.Query query) {
        return super.phone(query);
    }

    @Override
    protected UserVO getUser(LoginCmd.Query query) {
        return userRepository.getByPhone(query.getPhone());
    }
}
