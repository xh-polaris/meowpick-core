package com.xhpolaris.meowpick.trigger.http.security.authorize;

import com.xhpolaris.meowpick.domain.user.MeowUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
public class MeowAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private MeowUser user;
    private boolean  needChangePwd;
    private String session;
    private String lastLoginInfo;

    public MeowAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public MeowAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static MeowAuthenticationToken unauthorized(String token, String key) {
        return new MeowAuthenticationToken(token, key);
    }

    public static MeowAuthenticationToken authorized(MeowUser user) {
        MeowAuthenticationToken token = new MeowAuthenticationToken("", "", Collections.emptyList());

        token.user = user;

        return token;
    }
}
