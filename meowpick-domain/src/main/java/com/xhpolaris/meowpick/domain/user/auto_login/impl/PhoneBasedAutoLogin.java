package com.xhpolaris.meowpick.domain.user.auto_login.impl;

import com.xhpolaris.meowpick.common.enums.UserLoginEn;
import com.xhpolaris.meowpick.common.exceptions.NotFindException;
import com.xhpolaris.meowpick.domain.user.auto_login.AbstractAutoLogin;
import com.xhpolaris.meowpick.domain.user.model.entity.LoginCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.TokenVO;
import com.xhpolaris.meowpick.domain.user.model.valobj.UserVO;
import org.springframework.stereotype.Component;

@Component(UserLoginEn.Name.phone)
public class PhoneBasedAutoLogin extends AbstractAutoLogin {
//    @Override
//    public TokenVO phone(LoginCmd.Query query) {
//        UserVO user;
//        try {
//            user = userRepository.getByPhone(query.getPhone());
//        } catch (NotFindException ex) {
//            LoginCmd.CreateCmd cmd = new LoginCmd.CreateCmd();
//            cmd.setPhone(query.getPhone());
//            user = userRepository.registry(cmd);
//        }
//
//        return TokenVO.of(user);
//    }

    @Override
    protected void registryInfo(LoginCmd.Query query, LoginCmd.CreateCmd cmd) {
        cmd.setPhone(query.getPhone());
    }

    @Override
    protected UserVO getUser(LoginCmd.Query query) {
        return userRepository.getByPhone(query.getPhone());
    }
}
