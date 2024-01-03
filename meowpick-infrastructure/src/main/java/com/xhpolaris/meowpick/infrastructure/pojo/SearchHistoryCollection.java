package com.xhpolaris.meowpick.infrastructure.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.sql.Timestamp;

@Data
@Document("search_history")
public class SearchHistoryCollection {
    @MongoId
    private String id;

    private boolean deleted;
    private long count = 1;
    private boolean isUser;

    private String text;
    private String uid;
    private Timestamp createAt;
}
