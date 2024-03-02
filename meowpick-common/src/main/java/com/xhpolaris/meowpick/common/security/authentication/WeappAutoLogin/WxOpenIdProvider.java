package com.xhpolaris.meowpick.common.security.authentication.WeappAutoLogin;

import com.xhpolaris.meowpick.common.security.authentication.MeowAuthenticationToken;
import com.xhpolaris.meowpick.common.security.authorize.MeowUser;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Setter
public class WxOpenIdProvider extends AbstractUserDetailsAuthenticationProvider {
    private final UserDetailsService userDetailsService;

    public WxOpenIdProvider(UserDetailsService userDetailsService) {
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
        return MeowUser.anonymous();
//        return userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MeowAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
