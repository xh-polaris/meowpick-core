package com.xhpolaris.meowpick.trigger.http.api;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.aggregate.Course;
import com.xhpolaris.meowpick.domain.model.valobj.CourseCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "CourseApi", description = "课程接口")
@RequestMapping("/api/course")
public interface CourseApi {

    @PostMapping("/add")
    @Operation(summary = "新增课程")
    CourseVO add(@Validated @RequestBody CourseCmd.CreateCmd cmd);

    @PostMapping("/del/{id}")
    @Operation(summary = "删除课程")
    CourseVO del(@PathVariable String id);

    @PostMapping("/update")
    @Operation(summary = "更新课程")
    CourseVO update(@Validated @RequestBody CourseCmd.UpdateCmd cmd);

    @GetMapping("/query")
    @Operation(summary = "分页查询")
    PageEntity<CourseVO> query(@Validated @ParameterObject CourseCmd.Query query);

    @GetMapping("/{id}")
    @Operation(summary = "获取详情")
    Course get(@PathVariable String id);

    @PostMapping("/list")
    List<Course> list(@RequestBody List<String> courses);
}
