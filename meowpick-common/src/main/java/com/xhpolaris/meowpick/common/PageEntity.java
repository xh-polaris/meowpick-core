package com.xhpolaris.meowpick.common;

import lombok.Data;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
public class PageEntity<T> {
    private Long total;
    private List<T> rows = Collections.emptyList();

    @Data
    public static class Query {
        private Integer page = 0;
        private Integer size = 10;
        private String regex;
        private Date startAt;
        private Date  endAt;
    }
}
