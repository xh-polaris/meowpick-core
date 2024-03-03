package com.xhpolaris.meowpick.common.authorize;

import com.xhpolaris.meowpick.common.consts.Consts;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class MeowRememberMeAuthenticationToken extends RememberMeAuthenticationToken {
    private MeowUser user;

    public MeowRememberMeAuthenticationToken(Object principal,
                                             Collection<? extends GrantedAuthority> authorities) {
        super(Consts.Authorize.KEY, principal, authorities);
    }

    public static MeowRememberMeAuthenticationToken authorized(String id,
                                                               String name,
                                                               Collection<? extends GrantedAuthority> authorities) {
        MeowRememberMeAuthenticationToken token =
                new MeowRememberMeAuthenticationToken(name, authorities);

        token.user = new MeowUser(name, name, id, true);

        return token;
    }
}
