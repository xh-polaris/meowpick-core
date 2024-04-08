package com.xhpolaris.meowpick.domain.model.valobj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class UserCmd {
    private UserCmd(){}

    @Data
    @Schema(name = "UserCmd$CreateCmd")
    public static class CreateCmd{
        private String name;
        private String avatar;
    }

}
