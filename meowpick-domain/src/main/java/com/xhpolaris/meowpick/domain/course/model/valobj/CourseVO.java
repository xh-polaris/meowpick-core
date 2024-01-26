package com.xhpolaris.meowpick.domain.course.model.valobj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "CourseVO")
public class CourseVO {
    private String id;
    private String name;
    private String category;
    private String department;

    private String describe;


    private List<String> teachers;
    private List<String> campuses;
    private List<String> tags;

    //    院系
    private String depart;
    //    学分
    private String point;
}
