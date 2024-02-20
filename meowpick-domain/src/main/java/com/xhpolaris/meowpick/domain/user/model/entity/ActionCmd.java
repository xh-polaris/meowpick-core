package com.xhpolaris.meowpick.domain.user.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class ActionCmd {
    private ActionCmd() {}

    @Data
    @Schema(name = "ActionCmd$CreateCmd")
    public static class CreateCmd {
        private String emoji;
    }
}
