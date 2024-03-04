package com.xhpolaris.meowpick.domain.user.auto_login.impl;

import com.xhpolaris.meowpick.common.enums.UserLoginEn;
import com.xhpolaris.meowpick.domain.user.auto_login.AbstractAutoLogin;
import org.springframework.stereotype.Component;

@Component(UserLoginEn.Name.weapp)
public class WeappBasedAutoLogin extends AbstractAutoLogin {

}
