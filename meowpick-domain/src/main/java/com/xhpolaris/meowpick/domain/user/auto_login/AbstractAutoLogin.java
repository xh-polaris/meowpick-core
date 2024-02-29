package com.xhpolaris.meowpick.domain.user.auto_login;

import com.xhpolaris.meowpick.common.enums.UserLoginEn;
import com.xhpolaris.meowpick.common.exceptions.NotFindException;
import com.xhpolaris.meowpick.domain.user.model.entity.LoginCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.TokenVO;
import com.xhpolaris.meowpick.domain.user.model.valobj.UserVO;
import com.xhpolaris.meowpick.domain.user.repository.IUserRepository;
import com.xhpolaris.meowpick.domain.user.repository.IUserThirdLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractAutoLogin implements
                                        ILogin,
                                        IAutoLogin {
    @Autowired
    protected IUserRepository userRepository;
    @Autowired
    protected IUserThirdLoginRepository thirdLoginRepository;

    private final Map<UserLoginEn, Function<LoginCmd.Query, TokenVO>> loginMap =
            new HashMap<>();

    public AbstractAutoLogin() {
        loginMap.put(UserLoginEn.phone, this::phone);
        loginMap.put(UserLoginEn.weapp, this::weapp);
        loginMap.put(UserLoginEn.wx, this::wx);
        loginMap.put(UserLoginEn.qq, this::qq);
        loginMap.put(UserLoginEn.tiktok, this::tiktok);
        loginMap.put(UserLoginEn.weibo, this::weibo);
        loginMap.put(UserLoginEn.apple, this::apple);
        loginMap.put(UserLoginEn.google, this::google);
        loginMap.put(UserLoginEn.facebook, this::facebook);
        loginMap.put(UserLoginEn.twitter, this::twitter);
    }

    @Override
    public TokenVO autoLogin(LoginCmd.Query query) {
        UserVO user;
        try {
            user = getUser(query);
        } catch (NotFindException ex) {
            LoginCmd.CreateCmd cmd = new LoginCmd.CreateCmd();
            registryInfo(query, cmd);
            user = userRepository.registry(cmd);
        }

        return TokenVO.of(user);

//        return loginMap.get(query.getType()).apply(query);
    }

    protected abstract void registryInfo(LoginCmd.Query query, LoginCmd.CreateCmd cmd);

    protected abstract UserVO getUser(LoginCmd.Query query);

}
