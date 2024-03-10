package com.xhpolaris.meowpick.domain.teacher.model.valobj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class TeacherVO {
    private String id;
    private String avatar;
    private String name;
    private String depart;
    private String position;
}
