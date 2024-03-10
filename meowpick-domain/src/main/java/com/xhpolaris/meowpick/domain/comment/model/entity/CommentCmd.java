package com.xhpolaris.meowpick.domain.comment.model.entity;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.enums.CommentStatsEn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class CommentCmd {
    private CommentCmd() {}

    @Data
    @Schema(name = "CommentCmd$CreateCmd")
    public static class CreateCmd {
        @NotNull
        private String target;
        @NotNull
        private String text;
    }

    @Data
    @Schema(name = "CommentCmd$UpdateCmd")
    public static class UpdateCmd {
        @NotNull
        private String id;
        @NotNull
        private String text;
    }

    @Data
    @Schema(name = "CommentCmd$Query")
    public static class Query extends PageEntity.Query {
        private String id;
    }
}
