package com.xhpolaris.meowpick.infrastructure.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
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
