package com.xhpolaris.meowpick.common.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class MeowRememberMeAuthenticationToken extends RememberMeAuthenticationToken {
    private MeowUser user;

    public MeowRememberMeAuthenticationToken(String key, Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(key, principal, authorities);
    }
}
