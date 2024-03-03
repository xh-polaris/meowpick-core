package com.xhpolaris.meowpick.security.authentication.TokenBasedAutoLogin;

import com.xhpolaris.meowpick.common.JsonRet;
import com.xhpolaris.meowpick.common.authorize.MeowRememberMeAuthenticationToken;
import com.xhpolaris.meowpick.common.authorize.MeowUser;
import com.xhpolaris.meowpick.common.authorize.MeowUserDetailService;
import com.xhpolaris.meowpick.common.utils.RequestJsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class MeowpickTokenBasedRememberMeService extends NullRememberMeServices {
    private final MeowUserDetailService userDetailsService;
    private final UserDetailsChecker    userDetailsChecker = new AccountStatusUserDetailsChecker();

    private Collection<? extends GrantedAuthority> mapAuthorities(UserDetails user) {
        return user.getAuthorities();
    }

    protected MeowUser retrieveUser(String[] tokens) {
        return MeowUser.anonymous();
    }

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        String rememberMeToken = extractRememberMeToken(request);
        if (rememberMeToken == null || rememberMeToken.isEmpty()) {
            return null;
        }

        String[] tokens = decodeToken(rememberMeToken);
        MeowUser user   = retrieveUser(tokens);
        this.userDetailsChecker.check(user);

        return createSuccessFulAuthentication(request, user);
    }

    protected Authentication createSuccessFulAuthentication(HttpServletRequest request, MeowUser user) {
        return MeowRememberMeAuthenticationToken.authorized("65e43a7aa6fd34617f043a8e", user.getUsername(), mapAuthorities(user));
    }

    protected String[] decodeToken(String rememberMeToken) {
        return new String[0];
    }

    private String extractRememberMeToken(HttpServletRequest request) {
        String berryToken = request.getHeader("token");
        if (StringUtils.hasText(berryToken) && berryToken.startsWith("Berry ")) {
            return berryToken.substring(6);
        }

        return null;
    }

    @Override
    public void loginSuccess(HttpServletRequest request,
                             HttpServletResponse response,
                             Authentication successfulAuthentication) {
        System.out.println(successfulAuthentication);
        Response resp = new Response();

        resp.setToken("hello world rememberMe");

        RequestJsonUtils.write(JsonRet.then(resp));
    }

    @Data
    static class Response {
        private String token;
    }
}
