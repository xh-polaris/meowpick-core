package com.xhpolaris.meowpick.domain.model.valobj;

import com.xhpolaris.meowpick.common.PageEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

public class CommentCmd {
    private CommentCmd() {
    }

    @Data
    @Schema(name = "CommentCmd$CreateCmd")
    public static class CreateCmd {
        // 对应的课程id
        @NotNull
        private String target;

        // 评论的内容
        @NotNull
        private String text;

        // 次级评论应该为空
        private Integer score;

        // 评论标签
        private List<String> tags;

        // 一级评论id，为空则表示为一级评论
        private String firstLevelId;

        // 回复的用户的id，若一级评论应该为空
        private String replyTo;

        // 回答的评论id,一级评论应该为空
        private String parentId;
    }

    @Data
    @Schema(name = "CommentCmd$UpdateCmd")
    public static class UpdateCmd {
        @NotNull
        private String id;
        @NotNull
        private String text;

        @NotNull
        private String target;
        private List<String> tags;
    }

    @Data
    @Schema(name = "CommentCmd$Query")
    public static class Query extends PageEntity.Query {
        private String id;  //课程id

        private String firstLevelId; // 一级评论id

    }

    @Data
    @Schema(name = "CommentCmd$History")
    public static class History extends PageEntity.Query {
        private String uid;
        private String model;
    }
}
