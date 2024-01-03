package com.xhpolaris.meowpick.trigger.http;

import com.xhpolaris.meowpick.domain.teacher.service.TeacherServer;
import com.xhpolaris.meowpick.trigger.http.api.TeacherApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeacherController implements TeacherApi {
    private final TeacherServer service;


}
