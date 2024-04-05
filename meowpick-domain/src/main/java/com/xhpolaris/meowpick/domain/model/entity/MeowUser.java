package com.xhpolaris.meowpick.domain.model.entity;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MeowUser {
    private String  displayName;
    private String  userId;
    private String  avatar;
    private boolean auth;

    private boolean account_enable;
    private boolean account_lock;
    private boolean account_expire;
    private boolean account_credential;

    public MeowUser(String id,
                    String name,
                    boolean enable,
                    boolean expired,
                    boolean lock) {
        this.userId = id;
        this.displayName = name;
    }

    public MeowUser(String displayName,
                    String userId,
                    boolean accountUsable) {
        this.auth = accountUsable;
        this.displayName = displayName;
        this.userId = userId;
    }

    private static MeowUser anonymous = new MeowUser( "anonymous", "anonymous", true);

    public static MeowUser anonymous() {
        return anonymous;
    }

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
