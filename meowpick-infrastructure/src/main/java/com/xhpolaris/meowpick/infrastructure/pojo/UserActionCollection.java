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
@NoArgsConstructor
@AllArgsConstructor
@Document("user_action")
public class UserActionCollection {

    @Data
    public static class Action {
        private String target;
        private String emoji;
        @CreatedDate
        private Date crateAt;
        @LastModifiedDate
        private Date updateAt;
    }

    @MongoId
    private String id;
    private String uid;

    private List<Action> like = new ArrayList<>();
}
