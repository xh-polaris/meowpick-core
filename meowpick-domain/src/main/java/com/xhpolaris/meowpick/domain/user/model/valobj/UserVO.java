package com.xhpolaris.meowpick.domain.user.model.valobj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "UserVO")
public class UserVO {
    private String id;
    private String name;
    private String avatar;
}
