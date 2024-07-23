package com.xhpolaris.meowpick.infrastructure.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
@Document("coursePopularity")
@AllArgsConstructor
@NoArgsConstructor
public class CoursePopularityCollection {

    // 文档id
    @MongoId
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
    @CreatedDate  // 当你保存一个实体对象时，@CreatedDate 注解的字段会自动被设置为当前时间，表示实体的创建时间。
    private Date createAt;

    // 更新时间
    @LastModifiedDate  // 自动记录实体对象的最后修改时间
    private Date updateAt;
}
