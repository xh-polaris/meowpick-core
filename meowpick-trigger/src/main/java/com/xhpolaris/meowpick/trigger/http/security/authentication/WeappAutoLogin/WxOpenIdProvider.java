package com.xhpolaris.meowpick.trigger.http.security.authentication.WeappAutoLogin;

import com.xhpolaris.meowpick.trigger.http.security.authorize.MeowAuthenticationToken;
import com.xhpolaris.meowpick.domain.service.user.MeowUser;
import com.xhpolaris.meowpick.trigger.http.security.authorize.MeowUserDetailService;
import lombok.Setter;
import org.springframework.security.authentication.BadCredentialsException;
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

    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal,
                                                         Authentication authentication,
                                                         UserDetails user) {
        return MeowAuthenticationToken.authorized((MeowUser) user);
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
    throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException("");
        }
        return userDetailsService.loadUserByUsername(authentication.getCredentials().toString());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MeowAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
