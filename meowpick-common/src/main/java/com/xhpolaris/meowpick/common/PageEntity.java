package com.xhpolaris.meowpick.common;

import lombok.Data;

import java.util.Date;

@Data
public class PageEntity<T> {
    private Long total;
    private T rows;

    @Data
    public static class Query {
        private Integer page;
        private Integer size;
        private String regex;
        private Date startAt;
        private Date endAt;
    }
}
