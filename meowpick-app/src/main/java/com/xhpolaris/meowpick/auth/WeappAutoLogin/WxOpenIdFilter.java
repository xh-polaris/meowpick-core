package com.xhpolaris.meowpick.auth.WeappAutoLogin;

import com.xhpolaris.meowpick.auth.AbstractSecurityFilter;
import com.xhpolaris.meowpick.auth.MeowAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public class WxOpenIdFilter extends AbstractSecurityFilter {
    @Override
    protected Authentication buildToken(HttpServletRequest request) {
        return MeowAuthenticationToken.unauthorized(obtainParameter("wx-openid"));
    }
}
