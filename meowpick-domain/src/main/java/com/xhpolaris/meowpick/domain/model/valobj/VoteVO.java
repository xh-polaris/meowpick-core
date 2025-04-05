package com.xhpolaris.meowpick.domain.model.valobj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "VoteVO")
public class VoteVO {
    // 要投票的VoteStats的id
    private String id;

    // 自行生成的关联id，和课程实际存入数据库的id并不对应
    private String courseId;

    // 投票用户
    private UserVO user;

    // 投票类型（通过or不通过）
    private Integer voteType;

    // 投票时间
    private Long voteTime;

}