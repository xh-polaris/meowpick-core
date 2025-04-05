package com.xhpolaris.meowpick.domain.model.valobj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "VoteStatsVO")
public class VoteStatsVO {
    private String id;

    // 自行生成的关联id，和课程实际存入数据库的id并不对应
    private String courseId;

    // 关联课程详细信息
    private CourseVO courseDetail;

    // 通过的投票
    private Integer passVotes;

    // 不通过的投票
    private Integer rejectVotes;

    // 总票数
    private Integer totalVotes;

    // 公示开始时间
    private Long startTime;

    // 公示结束时间
    private Long endTime;

    // 是否已达到发布标准
    private Boolean isApproved;
}