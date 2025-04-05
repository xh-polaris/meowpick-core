package com.xhpolaris.meowpick.domain.model.valobj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Schema(name = "ReplyVO")
public class ReplyVO {
    private String id;
    private String uid;
    private String text;
    private Date crateAt;
    private Date  updateAt;
}
