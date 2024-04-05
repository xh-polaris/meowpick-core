package com.xhpolaris.meowpick.trigger.http.api;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.teacher.model.entity.TeacherCmd;
import com.xhpolaris.meowpick.domain.teacher.model.valobj.TeacherVO;
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

@Tag(name = "TeacherApi", description = "教师接口")
@RequestMapping("/api/teacher")
public interface TeacherApi {
    @PostMapping("/add")
    @Operation(summary = "新增")
    TeacherVO add(@Validated @RequestBody TeacherCmd.CreateCmd cmd);

    @PostMapping("/del/{id}")
    @Operation(summary = "删除")
    TeacherVO del(@PathVariable String id);

    @PostMapping("/update")
    @Operation(summary = "更新")
    TeacherVO update(@Validated @RequestBody TeacherCmd.UpdateCmd cmd);

    @GetMapping("/query")
    @Operation(summary = "分页查询")
    PageEntity<TeacherVO> query(@Validated @ParameterObject TeacherCmd.Query query);

    @GetMapping("/{id}")
    @Operation(summary = "获取详情")
    TeacherVO get(@PathVariable String id);

    @PostMapping("/batch")
    List<TeacherVO> getBatch(@RequestBody List<String> id);

}
