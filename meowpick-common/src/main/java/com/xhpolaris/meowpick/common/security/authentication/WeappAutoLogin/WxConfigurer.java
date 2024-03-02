package com.xhpolaris.meowpick.common.security.authentication.WeappAutoLogin;

import com.xhpolaris.meowpick.common.security.AbstractSecurityFilter;
import com.xhpolaris.meowpick.common.security.SecurityConfigurer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WxConfigurer extends SecurityConfigurer {
    private final UserDetailsService userDetailsService;

    @Override
    protected void postInit(List<AuthenticationProvider> providers) {
        providers.add(new WxOpenIdProvider(userDetailsService));
    }

    @Override
    protected AbstractSecurityFilter buildFilter() {
        this.successHandler((req, res, auth) -> {
            Response resp = new Response();

            resp.setToken("hello world 123");

            write(resp);
        });
        return new WxOpenIdFilter();
    }

    @Data
    static class Response {
        private String token;
    }
}
