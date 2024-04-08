package com.xhpolaris.meowpick.domain.model.valobj;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.enums.TypeEn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class SearchCmd {
    private SearchCmd() {}
    @Data
    @Schema(name = "SearchCmd$Query")
    public static class Query extends PageEntity.Query {
        @NotEmpty
        private String keyword;
        @NotNull
        private TypeEn type;
    }
}
