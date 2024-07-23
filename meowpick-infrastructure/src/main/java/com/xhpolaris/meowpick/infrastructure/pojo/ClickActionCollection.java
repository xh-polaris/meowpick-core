package com.xhpolaris.meowpick.infrastructure.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document("clickActionCollection")
@AllArgsConstructor
@NoArgsConstructor
public class ClickActionCollection {

    // 记录用户对于课程的点击行为类
    @Data
    @AllArgsConstructor
    public static class Action {

        // 用户id
        private String uid;

        // 点击时间
        @CreatedDate
        private Date createAt;
    }

    // 文档id
    @MongoId
    private String id;

    // 被点击的课程id
    private String target;

    // 课程被点击的次数
    private Long clickCount;

    // 被点击的课程的点击行为
    private List<ClickActionCollection.Action> click = new ArrayList<>();

    // 创建时间
    @CreatedDate  // 当你保存一个实体对象时，@CreatedDate 注解的字段会自动被设置为当前时间，表示实体的创建时间。
    private Date createAt;

    // 更新时间
    @LastModifiedDate  // 自动记录实体对象的最后修改时间
    private Date updateAt;
}
