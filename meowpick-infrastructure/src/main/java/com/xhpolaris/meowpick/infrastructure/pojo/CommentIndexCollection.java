package com.xhpolaris.meowpick.infrastructure.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("comment_index")
public class CommentIndexCollection {
    @MongoId
    private String id;
    @Indexed
    private String foreignId;
    private String commentId;
    private String moduleType;
    private String commentStats;

    private Timestamp createAt;
    private Timestamp updateAt;

}
