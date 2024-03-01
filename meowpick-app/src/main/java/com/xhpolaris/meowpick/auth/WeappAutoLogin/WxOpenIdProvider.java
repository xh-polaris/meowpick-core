package com.xhpolaris.meowpick.auth.WeappAutoLogin;

import com.xhpolaris.meowpick.auth.MeowAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class WxOpenIdProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws
                                                                      AuthenticationException {
        String token = authentication.getPrincipal().toString();
        if (token.equals("123456")) {
            return MeowAuthenticationToken.authorized("1", "eicen", true);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MeowAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
