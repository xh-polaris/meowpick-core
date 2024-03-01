package com.xhpolaris.meowpick.security.authorize.TokenBasedAutoLogin;

import com.xhpolaris.meowpick.security.AbstractSecurityFilter;
import com.xhpolaris.meowpick.security.SecurityConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenConfigurer extends SecurityConfigurer {
    private final UserDetailsService userDetailsService;

    @Override
    protected AbstractSecurityFilter buildFilter() {
        return new TokenFilter(userDetailsService);
    }
}
