package com.xhpolaris.meowpick.domain.user.model.valobj;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ActionVO {

    @Data
    public static class Action {
        private String uid;
        private String emoji;
        private Timestamp crateAt;
    }


    private List<Action> like;
}
