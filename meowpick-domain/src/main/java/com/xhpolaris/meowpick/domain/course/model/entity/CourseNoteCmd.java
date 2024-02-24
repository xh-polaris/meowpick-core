package com.xhpolaris.meowpick.domain.course.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class CourseNoteCmd {
    private CourseNoteCmd() {}

    @Data
    @Schema(name = "CourseNote$Query")
    public static class CreateCmd {
        private String title;
        private String text;
        private Float score;
    }

    @Data
    @Schema(name = "CourseNote$Query")
    public static class UpdateCmd {

    }

    @Data
    @Schema(name = "CourseNote$Query")
    public static class Query {

    }
}
