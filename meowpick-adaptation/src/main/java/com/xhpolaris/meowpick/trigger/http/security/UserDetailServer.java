package com.xhpolaris.meowpick.trigger.http.security;

import com.xhpolaris.meowpick.common.enums.UserLoginEn;
import com.xhpolaris.meowpick.common.exceptions.BizException;
import com.xhpolaris.meowpick.domain.service.login.factory.LoginProvider;
import com.xhpolaris.meowpick.domain.model.valobj.LoginCmd;
import com.xhpolaris.meowpick.domain.model.valobj.UserVO;
import com.xhpolaris.meowpick.domain.repository.IUserRepository;
import com.xhpolaris.meowpick.domain.model.entity.MeowUser;
import com.xhpolaris.meowpick.trigger.http.security.authorize.MeowUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserDetailServer implements MeowUserDetailService {
    private final LoginProvider   loginProvider;
    private final IUserRepository userRepository;

    public User of(MeowUser user) {
        return new User(
                user.getDisplayName(), "",
                user.isEnable(),
                user.isExpire(),
                user.isCredential(),
                user.isLock(),
                Collections.emptyList());
    }

    @Override
    public MeowUser loadUserByToken(UserLoginEn loginType, String token) {
        LoginCmd.Query query = new LoginCmd.Query();
        query.setType(loginType);
        query.setToken(token);

        UserVO user = loginProvider.autoLogin(query);

        return MeowUser.authorized(user.getId(), user.getName(), user.isEnable(), user.isExpire(),
                user.isLock());
    }

    @Override
    public MeowUser loadUserById(String id) {
        try {
            UserVO user = userRepository.getById(id);

            return MeowUser.authorized(user.getId(), user.getName(), user.isEnable(), user.isExpire(),
                    user.isLock());
        } catch (BizException e) {
            return loadUserByToken(UserLoginEn.meowchat, id);
        }
    }
}
