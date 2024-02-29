package com.xhpolaris.meowpick.common.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class MeowAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private MeowUser user;
    private boolean needChangePwd;
    private String session;
    private String lastLoginInfo;
    private Object data;

    public MeowAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials, new ArrayList());
    }

    public MeowAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
