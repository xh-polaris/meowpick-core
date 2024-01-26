package com.xhpolaris.meowpick.domain.course.model.entity;

import com.xhpolaris.meowpick.common.PageEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class CourseCmd {
    private CourseCmd() {}

    @Data
    @Schema(name = "CourseCmd$CreateCmd")
    public static class CreateCmd {
        private String name;
        private String category;
        private String department;
        private String depart;
        private String point;
        private String describe;
        private List<String> teachers;
        private List<String> campuses;
        private List<String> tags;
    }

    @Data
    @Schema(name = "CourseCmd$UpdateCmd")
    public static class UpdateCmd {
        private String id;
        private String name;
        private String category;
        private String department;
        private String depart;
        private String point;
        private String describe;
        private List<String> teachers;
        private List<String> campuses;
        private List<String> tags;
    }

    @Data
    @Schema(name = "CourseCmd$Query")
    public static class Query extends PageEntity.Query {
        private String name;
        private String category;
        private String department;
        private String depart;
        private String point;

        private List<String> teachers;
        private List<String> campuses;

        public static Query of(String text, PageEntity.Query q) {
            Query query = new Query();

            BeanUtils.copyProperties(q,query);

            query.setName(text);
            query.setCategory(text);
            query.setDepart(text);
            query.setDepartment(text);
            query.setPoint(text);
            query.setTeachers(List.of(text));
            query.setCampuses(List.of(text));

            return query;
        }
    }
}
