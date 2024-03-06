package com.xhpolaris.meowpick.common.authorize;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class MeowUser extends User {
    private String  displayName;
    private String  userId;
    private boolean byToken;
    private String  avatar;

    public MeowUser(String id,
                    String name,
                    boolean enable,
                    boolean expired,
                    boolean lock) {
        super(name, "", enable, expired, true, lock, List.of());
        this.userId = id;
        this.displayName = name;
    }

    public MeowUser(String username,
                    String displayName,
                    String userId,
                    boolean accountUsable) {
        super(username, "", accountUsable, true, true, true, Collections.emptyList());
        this.setDisplayName(displayName);
        this.setUserId(userId);
    }

    public static MeowUser getUser(Authentication auth) {
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

    public static MeowUser getCurrentUser() {
        return getUser(SecurityContextHolder.getContext().getAuthentication());
    }

    private static MeowUser anonymous = new MeowUser("anonymous", "anonymous", "anonymous", true);

    public static MeowUser anonymous() {
        return anonymous;
    }

    @Override
    public String getUsername() {
        return this.displayName;
    }

    public static MeowUser authorized(String id,
                                      String name,
                                      boolean enable,
                                      boolean expired,
                                      boolean lock) {
        return new MeowUser(id, name, enable, expired, lock);
    }
}
