package com.xhpolaris.meowpick.domain.model.entity;


import com.xhpolaris.idlgen.basic.UserMeta;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MeowUser {
    private UserMeta userMeta;
    private String  displayName;
    private String  userId;
    private String  avatar;
    private boolean auth;

    private boolean enable;
    private boolean lock;
    private boolean expire;
    private boolean credential;

    public MeowUser(String id,
                    String name,
                    boolean enable,
                    boolean expired,
                    boolean lock) {
        this.userId = id;
        this.displayName = name;
        this.enable = enable;
        this.lock = lock;
        this.expire = expired;
        this.credential = enable;
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
