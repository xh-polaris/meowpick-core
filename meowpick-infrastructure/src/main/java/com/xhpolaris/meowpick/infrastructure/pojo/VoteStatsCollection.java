package com.xhpolaris.meowpick.infrastructure.pojo;

import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Data
@Document("vote")
@AllArgsConstructor
@NoArgsConstructor
public class VoteStatsCollection {
    @MongoId
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

    // 投票过的用户ID列表
    private List<String> users;

    @CreatedDate
    private Date crateAt;
    @LastModifiedDate
    private Date updateAt;
}