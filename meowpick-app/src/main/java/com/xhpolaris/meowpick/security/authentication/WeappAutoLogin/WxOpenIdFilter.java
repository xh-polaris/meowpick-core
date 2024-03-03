package com.xhpolaris.meowpick.security.authentication.WeappAutoLogin;

import com.xhpolaris.meowpick.common.authorize.MeowAuthenticationToken;
import com.xhpolaris.meowpick.security.AbstractSecurityFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public class WxOpenIdFilter extends AbstractSecurityFilter {
    @Override
    protected Authentication buildToken(HttpServletRequest request) {
        return MeowAuthenticationToken.unauthorized(obtainParameter("key"), obtainParameter("wx-openid"));
    }
}
