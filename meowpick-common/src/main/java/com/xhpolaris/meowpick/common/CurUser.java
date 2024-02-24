package com.xhpolaris.meowpick.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurUser {
    private String id;
    private String avatar;
    private String nickName;

    private static CurUser anonymous = new CurUser("anonymous1", "avatar", "anonymous");
    public static CurUser anonymous() {
        return anonymous;
    }
}
