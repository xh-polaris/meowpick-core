package com.xhpolaris.meowpick.common.authorize;


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
    private String avatar;

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

        if (auth instanceof MeowAuthenticationToken token) {
            return token.getUser();
        } else if (auth instanceof MeowRememberMeAuthenticationToken token) {
            return token.getUser();
        }
//        else if (auth instanceof NsocAkSkAuthenticationToken token) {
//            return token.getUser();
//        }
        return anonymous();
    }

    private static MeowUser anonymous = new MeowUser("anonymous", "anonymous", "65e43a7aa6fd34617f043a8e", true);

    public static MeowUser anonymous() {
        return anonymous;
    }

    @Override
    public String getUsername() {
        return this.displayName;
    }
}
