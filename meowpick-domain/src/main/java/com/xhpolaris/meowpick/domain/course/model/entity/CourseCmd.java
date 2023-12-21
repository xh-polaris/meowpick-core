package com.xhpolaris.meowpick.domain.course.model.entity;

import com.xhpolaris.meowpick.common.PageEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class CourseCmd {
    private CourseCmd() {}

    @Data
    @Schema(name = "课程create")
    public static class CreateCmd {
        private String name;
        //    类别
        private String category;
        //    院系
        private String depart;
        //    教师
        private String teacher;

        //    校区
        private String campus;
        //    学分
        private String point;
    }

    @Data
    public static class UpdateCmd {

    }

    @Data
    public static class Query extends PageEntity.Query {

    }
}
