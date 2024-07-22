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
@Document("comment")
@AllArgsConstructor
@NoArgsConstructor
public class CommentCollection {

    @Data
    public static class Reply {
        private String id;
        private String text;
        private String uid;
        @CreatedDate
        private Date crateAt;
        @LastModifiedDate
        private Date updateAt;
    }

    private String id;

    private Integer score;
    private String target;
    private String text;
    private String uid;
    private List<Reply> replies = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    // 一级评论id，为空则表示为一级评论
    private String firstLevelId;

    // 回复的用户的id，一级评论应为空
    private String replyTo;

    // 回答的评论id,一级评论应为空
    private String parentId;

    @CreatedDate
    private Date crateAt;
    @LastModifiedDate
    private Date updateAt;
}
