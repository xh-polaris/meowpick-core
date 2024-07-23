package com.xhpolaris.meowpick.trigger.http.impl;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.entity.Course;
import com.xhpolaris.meowpick.domain.model.valobj.CourseCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.service.CourseServer;
import com.xhpolaris.meowpick.trigger.http.api.CourseApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<Course> list(List<String> courses) {
        return service.list(courses);
    }

    @Override
    public List<String> categoryList() {
        return service.categoryList();
    }

    @Override
    public List<String> departList() {
        return service.departList();
    }

    @Override
    public List<String> campusList() {
        return service.campusList();
    }

    @Override
    public Course get(String id) {
        return service.findById(id);
    }
}
