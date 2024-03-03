package com.xhpolaris.meowpick.security.authentication.WeappAutoLogin;

import com.xhpolaris.meowpick.common.authorize.MeowAuthenticationToken;
import com.xhpolaris.meowpick.common.authorize.MeowUser;
import com.xhpolaris.meowpick.common.authorize.MeowUserDetailService;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@Setter
public class WxOpenIdProvider extends AbstractUserDetailsAuthenticationProvider {
    private final MeowUserDetailService userDetailsService;

    public WxOpenIdProvider(MeowUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
    throws AuthenticationException {
        System.out.println(authentication);
    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal,
                                                         Authentication authentication,
                                                         UserDetails user) {
        MeowUser meowUser = (MeowUser) user;
        return MeowAuthenticationToken.authorized(meowUser.getUserId(), meowUser.getUsername());
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
    throws AuthenticationException {
        return userDetailsService.loadUserByUsername(username);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MeowAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
