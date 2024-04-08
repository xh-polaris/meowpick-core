package com.xhpolaris.meowpick.trigger.http.security.authentication.WeappAutoLogin;

import com.xhpolaris.meowpick.trigger.http.security.AbstractSecurityFilter;
import com.xhpolaris.meowpick.trigger.http.security.authorize.MeowAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public class WxOpenIdFilter extends AbstractSecurityFilter {
    @Override
    protected Authentication buildToken(HttpServletRequest request) {
        return MeowAuthenticationToken.unauthorized(obtainParameter("key"), obtainParameter("wx-openid"));
    }
}
