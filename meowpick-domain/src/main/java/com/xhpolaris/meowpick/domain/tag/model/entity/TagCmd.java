package com.xhpolaris.meowpick.domain.tag.model.entity;

import com.xhpolaris.meowpick.common.PageEntity;
import lombok.Data;

public class TagCmd {
    private TagCmd() {}

    @Data
    public static class CreateCmd {

    }

    @Data
    public static class UpdateCmd {

    }

    @Data
    public static class Query extends PageEntity.Query {

    }
}
