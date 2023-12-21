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
        return null;
    }

    public CourseVO remove(String id) {
        return null;
    }

    public CourseVO exec(CourseCmd.UpdateCmd cmd) {
        return null;
    }

    public PageEntity<CourseVO> query(CourseCmd.Query query) {
        return null;
    }

    public CourseVO findById(String id) {
        return null;
    }
}
