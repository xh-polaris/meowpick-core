package com.xhpolaris.meowpick.auth.WeappAutoLogin;

import com.xhpolaris.meowpick.auth.AbstractSecurityConfigurer;
import com.xhpolaris.meowpick.auth.AbstractSecurityFilter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Component;

public class WxOpenIdConfigurer extends AbstractSecurityConfigurer {

    public WxOpenIdConfigurer() {

    }

    @Override
    protected AuthenticationProvider buildSecurityProvider() {
        return new WxOpenIdProvider();
    }

    @Override
    protected AbstractSecurityFilter buildSecurityFilter(RememberMeServices rememberMeServices) {
        WxOpenIdFilter filter = new WxOpenIdFilter();
        return filter;
    }
}
