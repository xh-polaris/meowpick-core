package com.xhpolaris.meowpick.trigger.http;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.course.model.entity.CourseCmd;
import com.xhpolaris.meowpick.domain.course.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.course.service.CourseServer;
import com.xhpolaris.meowpick.trigger.http.api.CourseApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CourseController implements CourseApi {
    private final CourseServer service;

    @Override
    public CourseVO add(CourseCmd.CreateCmd cmd) {
        return service.exec(cmd);
    }

    @Override
    public CourseVO del(String id) {
        return service.remove(id);
    }

    @Override
    public CourseVO update(CourseCmd.UpdateCmd cmd) {
        return service.exec(cmd);
    }

    @Override
    public PageEntity<CourseVO> query(CourseCmd.Query query) {
        return service.query(query);
    }

    @Override
    public CourseVO get(String id) {
        return service.findById(id);
    }
}
