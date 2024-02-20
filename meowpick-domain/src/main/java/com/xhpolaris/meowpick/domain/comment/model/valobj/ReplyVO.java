package com.xhpolaris.meowpick.domain.comment.model.valobj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "ReplyVO")
public class ReplyVO {
    private String id;
    private String uid;
    private String text;

    List<ReplyVO> replies;
}
