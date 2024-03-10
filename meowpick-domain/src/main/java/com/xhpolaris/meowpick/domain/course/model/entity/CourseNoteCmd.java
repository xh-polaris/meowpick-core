package com.xhpolaris.meowpick.domain.course.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

public class CourseNoteCmd {
    private CourseNoteCmd() {}

    @Data
    @Schema(name = "CourseNote$Query")
    public static class CreateCmd {
        private String title;
        private String text;
        private Integer score;
        private List<String> tags;
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
