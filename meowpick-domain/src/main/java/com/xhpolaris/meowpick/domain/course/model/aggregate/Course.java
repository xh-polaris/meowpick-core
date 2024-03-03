package com.xhpolaris.meowpick.domain.course.model.aggregate;

import com.xhpolaris.meowpick.domain.course.model.valobj.CourseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class Course {
    private CourseVO data;
    private Long leaned;
    private Long wanted;

    private Integer score;

    private boolean isLearn;
    private boolean isWant;
}
