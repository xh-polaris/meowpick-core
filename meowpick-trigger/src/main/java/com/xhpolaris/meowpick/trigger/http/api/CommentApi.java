package com.xhpolaris.meowpick.trigger.http.api;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.CommentCmd;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CommentVO;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "CommentApi", description = "评论接口")
@RequestMapping("/api/comment")
public interface CommentApi {

    @PostMapping("/add")
    @Operation(summary = "新增评论")
    CommentVO add(@Validated @RequestBody CommentCmd.CreateCmd cmd);

    @PostMapping("/del/{id}")
    @Operation(summary = "删除评论")
    CommentVO del(@PathVariable String id);

    @PostMapping("/update")
    @Operation(summary = "更新评论")
    CommentVO update(@Validated @RequestBody CommentCmd.UpdateCmd cmd);

    @GetMapping("/query")
    @Operation(summary = "分页查询")
    PageEntity<CommentVO> query(@Validated @ParameterObject CommentCmd.Query query);

    @GetMapping("/{id}")
    @Operation(summary = "获取详情")
    ReplyVO get(@PathVariable String id);

    @PostMapping("/reply/{id}")
    @Operation(summary = "新增回复")
    ReplyVO replyTo(@PathVariable String id, @RequestBody ReplyCmd.CreateCmd cmd);


    @PostMapping("/history")
    PageEntity<CommentVO> history(@Validated @RequestBody CommentCmd.History query);

}
