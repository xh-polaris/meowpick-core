package com.xhpolaris.meowpick.domain.course.service;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.course.model.entity.CourseCmd;
import com.xhpolaris.meowpick.domain.course.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.course.repository.ICourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServer {
    private final ICourseRepository courseRepository;

    public CourseVO exec(CourseCmd.CreateCmd cmd) {
        return courseRepository.createCourse(cmd);
    }

    public CourseVO remove(String id) {
        return courseRepository.remove(id);
    }

    public CourseVO exec(CourseCmd.UpdateCmd cmd) {
        return courseRepository.updateCourse(cmd);
    }

    public PageEntity<CourseVO> query(CourseCmd.Query query) {
        return courseRepository.page(query);
    }

    public CourseVO findById(String id) {
        return courseRepository.getById(id);
    }
}
