package com.xhpolaris.meowpick.domain.course.model.aggregate;

import com.xhpolaris.meowpick.common.utils.ScoreTransfor;
import com.xhpolaris.meowpick.domain.course.model.valobj.CourseNote;
import com.xhpolaris.meowpick.domain.course.model.valobj.CourseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class Course {
    private CourseVO            data;
    private ScoreTransfor.Score score;
    private Long                learn_cnt;
    private Long                want_cnt;
    private CourseNote          notes;
}
