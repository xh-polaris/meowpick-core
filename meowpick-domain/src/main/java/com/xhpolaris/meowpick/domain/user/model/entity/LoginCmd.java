package com.xhpolaris.meowpick.domain.user.model.entity;

import com.xhpolaris.meowpick.common.enums.UserLoginEn;
import lombok.Data;

public class LoginCmd {
    private LoginCmd() {}

    @Data
    public static class CreateCmd {
        private String name;
        private String avatar;
        private String phone;
        private String email;
        private String password;
    }

    @Data
    public static class Query {
        private String email;
        private String username;
        private String password;
        private String phone;
        private String code;
        private String token;
        private UserLoginEn type;
    }
}
