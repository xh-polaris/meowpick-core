package com.xhpolaris.meowpick.domain.comment.model.entity;

import com.xhpolaris.meowpick.common.PageEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class ReplyCmd {
    private ReplyCmd() {}

    @Data
    @Schema(name = "ReplyCmd$CreateCmd")
    public static class CreateCmd {
        @NotNull
        private String text;
    }

    @Data
    @Schema(name = "ReplyCmd$UpdateCmd")
    public static class UpdateCmd {
        @NotNull
        private String id;
        @NotNull
        private String text;
    }

    @Data
    @Schema(name = "ReplyCmd$Query")
    public static class Query extends PageEntity.Query {
        private String id;
    }
}
