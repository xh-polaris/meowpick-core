package com.xhpolaris.meowpick.domain.course.model.entity;

import com.xhpolaris.meowpick.common.PageEntity;
import lombok.Data;

public class CourseCmd {
    private CourseCmd() {}

    @Data
    public static class CreateCmd {

    }

    @Data
    public static class UpdateCmd {

    }

    @Data
    public static class Query extends PageEntity.Query {

    }
}
