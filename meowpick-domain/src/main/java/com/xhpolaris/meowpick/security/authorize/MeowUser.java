package com.xhpolaris.meowpick.security.authorize;


import com.xhpolaris.meowpick.common.CurUser;
import com.xhpolaris.meowpick.security.authentication.MeowAuthenticationToken;
import com.xhpolaris.meowpick.security.authentication.MeowRememberMeAuthenticationToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
public class MeowUser extends User {
    private String  displayName;
    private String  userId;
    private boolean byToken;

    public MeowUser(String username,
                    String displayName,
                    String userId,
                    Collection<? extends GrantedAuthority> authorities) {
        super(username, "", authorities);
        this.setDisplayName(displayName);
        this.setUserId(userId);
    }

    public MeowUser(String username,
                    String displayName,
                    String userId,
                    boolean accountUsable) {
        super(username, "", accountUsable, true, true, true, Collections.emptyList());
        this.setDisplayName(displayName);
        this.setUserId(userId);
    }

    public static MeowUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }

        if (auth instanceof MeowAuthenticationToken token) {
            return token.getUser();
        } else if (auth instanceof MeowRememberMeAuthenticationToken token) {
            return token.getUser();
        }
//        else if (auth instanceof NsocAkSkAuthenticationToken token) {
//            return token.getUser();
//        }
        else {
            return null;
        }
    }

    public CurUser get() {
        CurUser user = new CurUser();

        user.setId(this.userId);
        user.setNickName(this.displayName);

        return user;
    }
}
