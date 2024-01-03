package com.xhpolaris.meowpick.domain.search.model.entity;

import com.xhpolaris.meowpick.common.PageEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class SearchCmd {
    private SearchCmd() {}
    @Data
    @Schema(name = "SearchCmd$Query")
    public static class Query extends PageEntity.Query {
        private String keyword;
        private String category;
    }
}
