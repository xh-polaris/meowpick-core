package com.xhpolaris.meowpick.domain.course.model.valobj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(name = "课程返回对象")
public class CourseVO {
    private String id;
    //    名称
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

    private Date crateAt;
    private Date updateAt;
}
