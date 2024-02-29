package com.xhpolaris.meowpick.domain.user.auto_login.factory;

import com.xhpolaris.meowpick.domain.user.auto_login.IAutoLogin;
import com.xhpolaris.meowpick.domain.user.auto_login.ILogin;
import com.xhpolaris.meowpick.domain.user.model.entity.LoginCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.TokenVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoginProvider implements IAutoLogin {
    private final Map<String, IAutoLogin> loginMap;

    @Override
    public TokenVO autoLogin(LoginCmd.Query query) {
        IAutoLogin handle = Optional.ofNullable(loginMap.get(query.getType()
                                                                  .getValue()))
                                    .orElseThrow(() -> new IllegalArgumentException(""));
        return handle.autoLogin(query);
    }
}
