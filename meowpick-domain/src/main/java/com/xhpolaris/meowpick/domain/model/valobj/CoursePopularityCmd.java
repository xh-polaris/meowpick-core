package com.xhpolaris.meowpick.domain.model.valobj;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class CoursePopularityCmd {

    private CoursePopularityCmd() {};

    @Data
    @Builder
    @Schema(name = "CoursePopularityCmd$CreateCmd")
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateCmd {

        // 课程id
        @NotNull
        private String courseId;

        // 课程的总热度
        @NotNull
        private Double popularity;

        // 课程的日热度
        @NotNull
        private Double dailyPopularity;

        // 课程的总点击次数
        @NotNull
        private Long clickCount;

        // 课程的日点击次数
        @NotNull
        private Long dailyClickCount;

        // 课程的总吐槽次数
        @NotNull
        private Long commentCount;

        // 课程的日吐槽次数
        @NotNull
        private Long dailyCommentCount;

        // 课程吐槽的总点赞次数
        @NotNull
        private Long likeCount;

        // 课程吐槽的日点赞次数
        @NotNull
        private Long dailyLikeCount;
    }

    @Data
    @Builder
    @Schema(name = "CoursePopularityCmd$UpdateCmd")
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateCmd {

        // 文档id
        @NotNull
        private String id;

        // 课程id
        @NotNull
        private String courseId;

        // 课程的总热度
        @NotNull
        private Double popularity;

        // 课程的日热度
        @NotNull
        private Double dailyPopularity;

        // 课程的总点击次数
        @NotNull
        private Long clickCount;

        // 课程的日点击次数
        @NotNull
        private Long dailyClickCount;

        // 课程的总吐槽次数
        @NotNull
        private Long commentCount;

        // 课程的日吐槽次数
        @NotNull
        private Long dailyCommentCount;

        // 课程吐槽的总点赞次数
        @NotNull
        private Long likeCount;

        // 课程吐槽的日点赞次数
        @NotNull
        private Long dailyLikeCount;
    }

    @Data
    @Builder
    @Schema(name = "CoursePopularityCmd$Query")
    public static class Query {
        private Integer page = 1;
        private Integer size = 10;
    }
}
