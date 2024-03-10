package com.xhpolaris.meowpick.domain.teacher.model.entity;

import com.xhpolaris.meowpick.common.PageEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class TeacherCmd {
    private TeacherCmd() {}

    @Data
    @Schema(name = "TeacherCmd$CreateCmd")
    public static class CreateCmd {
        private String avatar;
        private String name;
        private String depart;
        private String position;
    }

    @Data
    @Schema(name = "TeacherCmd$UpdateCmd")
    public static class UpdateCmd {
        private String id;
        private String avatar;
        private String name;
        private String depart;
        private String position;
    }

    @Data
    @Schema(name = "TeacherCmd$Query")
    public static class Query extends PageEntity.Query {

    }
}
