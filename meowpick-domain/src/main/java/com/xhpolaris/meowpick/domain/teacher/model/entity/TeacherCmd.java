package com.xhpolaris.meowpick.domain.teacher.model.entity;

import com.xhpolaris.meowpick.common.PageEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class TeacherCmd {
    private TeacherCmd() {}

    @Data
    @Schema(name = "TeacherCmd$CreateCmd")
    public static class CreateCmd {

    }

    @Data
    @Schema(name = "TeacherCmd$UpdateCmd")
    public static class UpdateCmd {

    }

    @Data
    @Schema(name = "TeacherCmd$Query")
    public static class Query extends PageEntity.Query {

    }
}
