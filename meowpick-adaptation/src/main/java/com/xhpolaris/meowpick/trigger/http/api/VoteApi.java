package com.xhpolaris.meowpick.trigger.http.api;

import com.xhpolaris.meowpick.domain.model.valobj.VoteStatsVO;
import com.xhpolaris.meowpick.domain.model.valobj.VoteVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "VoteApi", description = "投票接口")
@RequestMapping("/api/vote")
public interface VoteApi {

    @PostMapping("/add")
    @Operation(summary = "新增课程投票")
    VoteStatsVO add(@Validated @RequestBody VoteStatsVO voteStatsVO);

    @PostMapping("/execute")
    @Operation(summary = "进行投票操作")
    VoteStatsVO execute(@Validated @RequestBody VoteVO voteVO);


}
