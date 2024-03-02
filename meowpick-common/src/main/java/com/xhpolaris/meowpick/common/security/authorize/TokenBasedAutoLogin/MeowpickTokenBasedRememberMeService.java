package com.xhpolaris.meowpick.common.security.authorize.TokenBasedAutoLogin;

import com.xhpolaris.meowpick.common.security.authentication.MeowRememberMeAuthenticationToken;
import com.xhpolaris.meowpick.common.security.authorize.MeowUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.util.StringUtils;

import java.util.Collection;

public class MeowpickTokenBasedRememberMeService extends NullRememberMeServices {
    private final UserDetailsService userDetailsService;
    private final UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();

    public MeowpickTokenBasedRememberMeService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private Collection<? extends GrantedAuthority> mapAuthorities(UserDetails user) {
        return user.getAuthorities();
    }

    private UserDetails retrieveUser(String[] tokens) {
        return MeowUser.anonymous();

//        UserDetails loadedUser = userDetailsService.loadUserByUsername(tokens[0]);
//        if (loadedUser == null) {
//            throw new NotFindException("");
//        }
//
//        return loadedUser;
    }

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        String rememberMeToken = extractRememberMeToken(request);
        if (rememberMeToken == null || rememberMeToken.length() == 0) {
            return null;
        }

        String[]    tokens = decodeToken(rememberMeToken);
        UserDetails user   = retrieveUser(tokens);
        this.userDetailsChecker.check(user);

        return createSuccessFulAuthentication(request, (MeowUser) user);
    }

    private Authentication createSuccessFulAuthentication(HttpServletRequest request, MeowUser user) {
        return MeowRememberMeAuthenticationToken.authorized("1", user.getUsername(), mapAuthorities(user));
    }

    private String[] decodeToken(String rememberMeToken) {
        return new String[0];
    }

    private String extractRememberMeToken(HttpServletRequest request) {
        String berryToken = request.getHeader("token");
        if (StringUtils.hasText(berryToken) && berryToken.startsWith("Berry ")) {
            return berryToken.substring(6);
        }

        return null;
    }
}
