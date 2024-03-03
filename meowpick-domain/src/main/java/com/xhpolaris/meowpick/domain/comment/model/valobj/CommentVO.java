package com.xhpolaris.meowpick.domain.comment.model.valobj;

import com.xhpolaris.meowpick.domain.user.model.valobj.ActionVO;
import com.xhpolaris.meowpick.domain.user.model.valobj.UserVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(name = "CommentVO")
public class CommentVO {
    private String id;
    private String uid;
    private String text;
    private Date crateAt;
    private Date updateAt;

    private Integer reply;

    private ActionVO relation;

    private UserVO user;
}
