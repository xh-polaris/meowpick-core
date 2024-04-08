package com.xhpolaris.meowpick.domain.model.valobj;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ActionVO {

    @Data
    public static class Action {
        private String uid;
        private String emoji;
        private Date crateAt;
    }

    private boolean like;
    private Integer like_cnt;
    private boolean share;

    private List<Action> likes = new ArrayList<>();
}
