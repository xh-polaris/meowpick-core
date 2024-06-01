package com.xhpolaris.meowpick.domain.model.valobj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Schema(name = "CourseVO")
public class CourseVO {
    private String id;
    private String name;
    private String category;
    //    院系
    private String department;
    private List<List<String>> link;
    //    绩点
    private Integer point;
    //    描述
    private String describe;
    private List<String> teachers = new ArrayList<>();
    private List<String> campuses = new ArrayList<>();

    private Map<String, Integer> tagCount;
}
