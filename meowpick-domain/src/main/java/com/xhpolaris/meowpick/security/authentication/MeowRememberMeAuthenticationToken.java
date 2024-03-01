package com.xhpolaris.meowpick.security.authentication;

import com.xhpolaris.meowpick.security.authorize.MeowUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class MeowRememberMeAuthenticationToken extends RememberMeAuthenticationToken {
    private MeowUser user;

    public MeowRememberMeAuthenticationToken(String key,
                                             Object principal,
                                             Collection<? extends GrantedAuthority> authorities) {
        super(key, principal, authorities);
    }

    public static MeowRememberMeAuthenticationToken authorized(String id,
                                                               String name,
                                                               Collection<? extends GrantedAuthority> authorities) {
        MeowRememberMeAuthenticationToken token =
                new MeowRememberMeAuthenticationToken(id, name, authorities);

        token.user = new MeowUser(name, name, id, true);

        return token;
    }
}
