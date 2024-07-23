package com.xhpolaris.meowpick.domain.model.valobj;

import lombok.Data;

import java.util.Date;

@Data
public class CoursePopularityVO {

    // 文档id
    private String id;

    // 课程id
    private String courseId;

    // 课程的总热度
    private Double popularity;

    // 课程的日热度
    private Double dailyPopularity;

    // 课程的总点击次数
    private Long clickCount;

    // 课程的日点击次数
    private Long dailyClickCount;

    // 课程的总吐槽次数
    private Long commentCount;

    // 课程的日吐槽次数
    private Long dailyCommentCount;

    // 课程吐槽的总点赞次数
    private Long likeCount;

    // 课程吐槽的日点赞次数
    private Long dailyLikeCount;

    // 创建时间
    private Date createAt;

    // 更新时间
    private Date updateAt;

    // 课程信息
    private CourseVO course;
}
