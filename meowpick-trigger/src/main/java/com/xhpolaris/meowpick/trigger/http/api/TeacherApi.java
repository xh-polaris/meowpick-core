package com.xhpolaris.meowpick.trigger.http.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "TeacherApi", description = "教师接口")
@RequestMapping("/api/teacher")
public interface TeacherApi {
}
