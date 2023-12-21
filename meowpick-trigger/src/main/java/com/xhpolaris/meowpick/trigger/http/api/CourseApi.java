package com.xhpolaris.meowpick.trigger.http.api;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.course.model.entity.CourseCmd;
import com.xhpolaris.meowpick.domain.course.model.valobj.CourseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "课程接口")
@RequestMapping("/api/course")
public interface CourseApi {

    @Operation(summary = "新增课程")
    @PostMapping("/add")
    CourseVO add(@Validated @RequestBody CourseCmd.CreateCmd cmd);

    @Operation(summary = "删除课程")
    @PostMapping("/del/{id}")
    CourseVO del(@PathVariable String id);

    @Operation(summary = "更新课程")
    @PostMapping("/update")
    CourseVO update(@Validated @RequestBody CourseCmd.UpdateCmd cmd);

    @Operation(summary = "分页查询")
    @GetMapping("/query")
    PageEntity<CourseVO> query(@Validated @ParameterObject CourseCmd.Query query);

    @Operation(summary = "获取详情")
    @GetMapping("/{id}")
    CourseVO get(@PathVariable String id);
}
