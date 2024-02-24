package com.xhpolaris.meowpick.infrastructure.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document("action")
@AllArgsConstructor
@NoArgsConstructor
public class ActionCollection {

    @Data
    public static class Action {
        private String uid;
        private String emoji;
        @CreatedDate
        private Date crateAt;
    }

    @MongoId
    private String id;
    private String target;

    private List<Action> like = new ArrayList<>();
}
