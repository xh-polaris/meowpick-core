package com.xhpolaris.meowpick.infrastructure.pojo;

import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("vote")
@AllArgsConstructor
@NoArgsConstructor
public class VoteStatsCollection {
    private String id;
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

    @CreatedDate
    private Date crateAt;
    @LastModifiedDate
    private Date updateAt;
}