package com.xhpolaris.meowpick.infrastructure.pojo;

import com.xhpolaris.meowpick.domain.course.model.entity.CourseCmd;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CourseMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document("course")
@AllArgsConstructor
@NoArgsConstructor
public class CourseCollection {
    @MongoId
    private String id;
    private String name;
    private String category;
    //    院系
    private String department;
    private String depart;
    //    绩点
    private String point;
    //    描述
    private String describe;
    private List<String> teachers = new ArrayList<>();
    private List<String> campuses = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    @CreatedDate
    private Date crateAt;
    @LastModifiedDate
    private Date updateAt;

    public static Example<CourseCollection> toExample(CourseCmd.Query query) {
        CourseCollection course = CourseMap.instance.query(query);

        ExampleMatcher.GenericPropertyMatcher containsMatcher = ExampleMatcher.GenericPropertyMatchers.contains()
                .ignoreCase();

        // 定义 ExampleMatcher，这里使用了忽略大小写的匹配规则
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("name", containsMatcher)
                .withMatcher("category", containsMatcher)
                .withMatcher("department", containsMatcher)
                .withMatcher("depart", containsMatcher)
                .withMatcher("point", containsMatcher)
                .withMatcher("teachers", containsMatcher)
                .withMatcher("campuses", containsMatcher)
                .withIgnoreCase()
                .withIgnoreNullValues();

        return Example.of(course, matcher);
    }
}
