package com.xhpolaris.meowpick.support;

import com.xhpolaris.meowpick.common.authorize.MeowUser;
import com.xhpolaris.meowpick.common.authorize.MeowUserDetailService;
import com.xhpolaris.meowpick.common.enums.UserLoginEn;
import com.xhpolaris.meowpick.domain.user.auto_login.factory.LoginProvider;
import com.xhpolaris.meowpick.domain.user.model.entity.LoginCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.UserVO;
import com.xhpolaris.meowpick.domain.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailServer implements MeowUserDetailService {
    private final LoginProvider   loginProvider;
    private final IUserRepository userRepository;

    @Override
    public MeowUser loadUserByToken(UserLoginEn loginType, String token) {
        LoginCmd.Query query = new LoginCmd.Query();
        query.setType(loginType);
        query.setToken(token);

        UserVO user = loginProvider.autoLogin(query);

        return MeowUser.authorized(user.getId(), user.getName(), user.isAccount_enable(), user.isAccount_expire(),
                user.isAccount_lock());
    }

    @Override
    public MeowUser loadUserById(String id) {
        try {
            UserVO user = userRepository.getById(id);

            return MeowUser.authorized(user.getId(), user.getName(), user.isAccount_enable(), user.isAccount_expire(),
                    user.isAccount_lock());
        } catch (Exception e) {
            return loadUserByToken(UserLoginEn.meowchat, id);
        }
    }
}
