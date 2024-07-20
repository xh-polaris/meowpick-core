package com.xhpolaris.meowpick.domain.model.valobj;

import com.xhpolaris.meowpick.common.PageEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

public class PopularityCmd {

    private PopularityCmd() {};

    @Data
    @Builder
    @Schema(name = "PopularityCmd$CreateCmd")
    public static class CreateCmd {
        @NotNull
        private String text;

        @NotNull
        private Double popularity;

        @NotNull
        private Long count;

        @NotNull
        private Boolean deleted;

        @NotNull
        private Date updateAt;

        @NotNull
        private Date createAt;
    }

    @Data
    @Builder
    @Schema(name = "PopularityCmd$UpdateCmd")
    public static class UpdateCmd {
        @NotNull
        private String id;

        @NotNull
        private String text;

        @NotNull
        private Double popularity;

        @NotNull
        private Long count;

        @NotNull
        private Boolean deleted;

        @NotNull
        private Date updateAt;

        @NotNull
        private Date createAt;
    }

    @Data
    @Builder
    @Schema(name = "PopularityCmd$Query")
    public static class Query {
        private Integer page = 0;
        private Integer size = 10;
    }
}
