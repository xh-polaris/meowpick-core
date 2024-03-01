package com.xhpolaris.meowpick.security.authorize.TokenBasedAutoLogin;

import com.xhpolaris.meowpick.common.exceptions.NotFindException;
import com.xhpolaris.meowpick.security.AbstractSecurityFilter;
import com.xhpolaris.meowpick.security.authentication.MeowRememberMeAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;

import java.util.Collection;

public class TokenFilter extends AbstractSecurityFilter {
    private final UserDetailsService userDetailsService;
    private final UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();

    public TokenFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldAuthentication() {
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.hasText(token) && token.startsWith("Berry ")) {
            token = token.substring(6);

            System.out.println(token);

            UserDetails user =  retrieveUser("1");
            preAuthenticationChecks.check(user);

            SecurityContextHolder.getContext()
                                 .setAuthentication(MeowRememberMeAuthenticationToken.authorized( "1",token, mapAuthorities(user)));
        }
    }

    private Collection<? extends GrantedAuthority> mapAuthorities(UserDetails user) {
        return user.getAuthorities();
    }

    private UserDetails retrieveUser(String id) {
        UserDetails loadedUser = userDetailsService.loadUserByUsername(id);
        if (loadedUser == null) {
            throw new NotFindException("");
        }

        return loadedUser;
    }

    static class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        @Override
        public void check(UserDetails toCheck) {

        }
    }
}
