package com.xhpolaris.meowpick.domain.model.valobj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "VoteVO")
public class VoteVO {
    private String id;

    // 关联的课程ID
    private String courseId;

    // 投票用户
    private UserVO user;

    // 投票类型（通过or不通过）
    private Integer voteType;

    // 投票时间
    private Long voteTime;

}