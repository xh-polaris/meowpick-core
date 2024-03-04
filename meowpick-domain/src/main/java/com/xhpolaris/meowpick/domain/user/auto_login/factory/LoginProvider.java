package com.xhpolaris.meowpick.domain.user.auto_login.factory;

import com.xhpolaris.meowpick.domain.user.auto_login.IAutoLogin;
import com.xhpolaris.meowpick.domain.user.model.entity.LoginCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoginProvider implements IAutoLogin {
    private final Map<String, IAutoLogin> loginMap;

    @Override
    public UserVO autoLogin(LoginCmd.Query query) {
        IAutoLogin handle = Optional.ofNullable(loginMap.get(query.getType().getValue()))
                                    .orElseThrow(() -> new IllegalArgumentException(""));
        return handle.autoLogin(query);
    }
}
