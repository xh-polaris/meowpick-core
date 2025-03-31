package com.xhpolaris.meowpick.trigger.http.api;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.VoteStatsCmd;
import com.xhpolaris.meowpick.domain.model.valobj.VoteStatsVO;
import com.xhpolaris.meowpick.domain.model.valobj.VoteVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "VoteApi", description = "投票接口")
@RequestMapping("/api/vote")
public interface VoteApi {

    @PostMapping("/add")
    @Operation(summary = "新增课程投票")
    VoteStatsVO add(@Validated @RequestBody VoteStatsCmd.CreateCmd cmd);

    @PostMapping("/execute")
    @Operation(summary = "进行投票操作")
    VoteStatsVO execute(@Validated @RequestBody VoteVO voteVO);

    @PostMapping("/del/{id}")
    @Operation(summary = "删除课程投票")
    VoteStatsVO del(@PathVariable String id);

    @PostMapping("/update")
    @Operation(summary = "更新课程投票数据")
    VoteStatsVO update(@Validated @RequestBody VoteStatsCmd.UpdateCmd cmd);

    @GetMapping("/query")
    @Operation(summary = "分页查询")
    PageEntity<VoteStatsVO> query(@Validated @ParameterObject VoteStatsCmd.Query query);
}
