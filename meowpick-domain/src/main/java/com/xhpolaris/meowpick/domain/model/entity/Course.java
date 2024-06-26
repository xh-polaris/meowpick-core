package com.xhpolaris.meowpick.domain.model.entity;

import com.xhpolaris.meowpick.common.utils.ScoreTransfor;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class Course {
    private CourseVO            data;
    private ScoreTransfor.Score score;
}
