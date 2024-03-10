package com.xhpolaris.meowpick.security.authentication.WeappAutoLogin;

import com.xhpolaris.meowpick.common.authorize.MeowUserDetailService;
import com.xhpolaris.meowpick.security.AbstractSecurityFilter;
import com.xhpolaris.meowpick.security.SecurityConfigurer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WxConfigurer extends SecurityConfigurer {
    private final MeowUserDetailService userDetailsService;
    private final RememberMeServices    rememberMeServices;

    @Override
    protected void postInit(List<AuthenticationProvider> providers) {
        rememberMeServices(rememberMeServices);
        providers.add(new WxOpenIdProvider(userDetailsService));
    }

    @Override
    protected AbstractSecurityFilter buildFilter() {
        return new WxOpenIdFilter();
    }

    @Data
    static class Response {
        private String token;
    }
}
