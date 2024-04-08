package com.xhpolaris.meowpick.domain.service.login;

import com.xhpolaris.meowpick.common.enums.UserLoginEn;
import com.xhpolaris.meowpick.common.exceptions.BizException;
import com.xhpolaris.meowpick.domain.model.valobj.LoginCmd;
import com.xhpolaris.meowpick.domain.model.valobj.UserVO;
import com.xhpolaris.meowpick.domain.repository.IUserRepository;
import com.xhpolaris.meowpick.domain.repository.IUserThirdLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractAutoLogin implements IAutoLogin, IUserLogin, IthirdTokenLogin {
    @Autowired
    protected IUserRepository           userRepository;
    @Autowired
    protected IUserThirdLoginRepository thirdLoginRepository;

    private final Map<UserLoginEn, Function<LoginCmd.Query, UserVO>> loginMap = new EnumMap<>(UserLoginEn.class);

    protected AbstractAutoLogin() {
        loginMap.put(UserLoginEn.meowchat,  this::meowchat);
        loginMap.put(UserLoginEn.phone,     this::phone);
        loginMap.put(UserLoginEn.email,     this::email);
        loginMap.put(UserLoginEn.password,  this::usernamePassword);

        loginMap.put(UserLoginEn.weapp,     this::third);
        loginMap.put(UserLoginEn.wx,        this::third);
        loginMap.put(UserLoginEn.qq,        this::third);
        loginMap.put(UserLoginEn.tiktok,    this::third);
        loginMap.put(UserLoginEn.weibo,     this::third);
        loginMap.put(UserLoginEn.apple,     this::third);
        loginMap.put(UserLoginEn.google,    this::third);
        loginMap.put(UserLoginEn.facebook,  this::third);
        loginMap.put(UserLoginEn.twitter,   this::third);
    }

    @Override
    public UserVO autoLogin(LoginCmd.Query query) {
        return loginMap.get(query.getType()).apply(query);
    }

    @Override
    public UserVO third(LoginCmd.Query query) {
        UserVO user;
        try {
            user = getUser(query);
        } catch (BizException ex) {
            LoginCmd.CreateCmd cmd = new LoginCmd.CreateCmd();
            cmd.setPhone(query.getPhone());
            cmd.setEmail(query.getEmail());
            cmd.setPassword(query.getPassword());

            cmd.setName("uid");

            user = userRepository.registry(cmd);
            registryInfo(user.getId(), query);
        }

        return user;
    }

    protected void registryInfo(String uid, LoginCmd.Query query) {
        thirdLoginRepository.registry(uid, query);
    }

    protected UserVO getUser(LoginCmd.Query query) {
        return userRepository.getById(thirdLoginRepository.getUid(query));
    }

}
