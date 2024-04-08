package com.xhpolaris.meowpick.domain.model.valobj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class TeacherVO {
    private String id;
    private String avatar;
    private String name;
    private String uid;
}
