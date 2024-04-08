package com.xhpolaris.meowpick.domain.service.login.factory;

import com.xhpolaris.meowpick.common.exceptions.BizException;
import com.xhpolaris.meowpick.domain.service.login.IAutoLogin;
import com.xhpolaris.meowpick.domain.model.valobj.LoginCmd;
import com.xhpolaris.meowpick.domain.model.valobj.UserVO;
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
                                    .orElseThrow(BizException::IllegalArgument);
        return handle.autoLogin(query);
    }
}
