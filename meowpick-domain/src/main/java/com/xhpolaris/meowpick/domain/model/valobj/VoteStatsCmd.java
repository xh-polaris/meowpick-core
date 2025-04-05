package com.xhpolaris.meowpick.domain.model.valobj;

import com.xhpolaris.meowpick.common.PageEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class VoteStatsCmd {
    private VoteStatsCmd() {}

    @Data
    @Schema(name = "VoteStatsCmd$CreateCmd")
    public static class CreateCmd {
        @NotNull
        private CourseVO courseDetail;
        private Long startTime;
        private Long endTime;
    }

    @Data
    @Schema(name = "VoteStatsCmd$UpdateCmd")
    public static class UpdateCmd {
        @NotNull
        private String id;
        private String courseId;
        private CourseVO courseDetail;
        private Long endTime;
    }

    @Data
    @Schema(name = "VoteStatsCmd$Query")
    public static class Query extends PageEntity.Query {
    }
}
