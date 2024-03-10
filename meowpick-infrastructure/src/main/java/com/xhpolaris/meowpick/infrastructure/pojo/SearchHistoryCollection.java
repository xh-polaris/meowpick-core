package com.xhpolaris.meowpick.infrastructure.pojo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
@Document("search_history")
public class SearchHistoryCollection {
    @MongoId
    private String id;

    private boolean deleted;
//    搜索次数
    private long count = 1;
    private boolean isUser;

//    搜索内容
    private String text;
    private String uid;
    @CreatedDate
    private Date createAt;
}
