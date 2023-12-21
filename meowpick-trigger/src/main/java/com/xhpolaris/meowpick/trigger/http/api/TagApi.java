package com.xhpolaris.meowpick.trigger.http.api;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.tag.model.entity.TagCmd;
import com.xhpolaris.meowpick.domain.tag.model.valobj.TagVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "标签接口")
@RequestMapping("/api/tag")
public interface TagApi {

    @Operation(summary = "新增标签")
    @PostMapping("/add")
    TagVO add(@Validated @RequestBody TagCmd.CreateCmd cmd);

    @Operation(summary = "删除标签")
    @PostMapping("/del/{id}")
    TagVO del(@PathVariable String id);

    @Operation(summary = "修改标签")
    @PostMapping("/update")
    TagVO update(@Validated @RequestBody TagCmd.UpdateCmd cmd);

    @Operation(summary = "分页查询")
    @GetMapping("/query")
    PageEntity<TagVO> query(@Validated @ParameterObject TagCmd.Query query);

    @Operation(summary = "获取标签")
    @GetMapping("/{id}")
    TagVO get(@PathVariable String id);
}
