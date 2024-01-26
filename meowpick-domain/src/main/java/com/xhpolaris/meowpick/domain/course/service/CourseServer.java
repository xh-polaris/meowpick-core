package com.xhpolaris.meowpick.domain.course.service;

import com.xhpolaris.meowpick.common.Context;
import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.SearchComponent;
import com.xhpolaris.meowpick.domain.course.model.aggregate.Course;
import com.xhpolaris.meowpick.domain.course.model.entity.CourseCmd;
import com.xhpolaris.meowpick.domain.course.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.course.repository.ICourseRepository;
import com.xhpolaris.meowpick.domain.search.model.entity.SearchCmd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServer implements SearchComponent<CourseVO> {
    private final ICourseRepository courseRepository;
    private final Context context;

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

    public Course findById(String id) {
        return courseRepository.getById(id, context.getUser().getId());
    }

    @Override
    public PageEntity<CourseVO> search(SearchCmd.Query query) {
        return this.query(CourseCmd.Query.of(query.getKeyword(), query));
    }

    public boolean learn(String id) {
        return courseRepository.learned(id, context.getUser().getId());
    }

    public boolean want(String id) {
        return courseRepository.want2learn(id, context.getUser().getId());
    }
}
