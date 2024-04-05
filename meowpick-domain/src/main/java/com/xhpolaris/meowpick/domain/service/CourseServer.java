package com.xhpolaris.meowpick.domain.service;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.utils.ScoreTransfor;
import com.xhpolaris.meowpick.domain.model.entity.Course;
import com.xhpolaris.meowpick.domain.model.valobj.CourseCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.repository.ICourseRepository;
import com.xhpolaris.meowpick.domain.model.valobj.SearchCmd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServer {
    private final ICourseRepository courseRepository;
    private final Context           context;
    private final CourseNoteServer  noteServer;

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
        Course vo = courseRepository.getById(id, context.uid());

        vo.setScore(ScoreTransfor.transfor(noteServer.list(id)));
        vo.setNotes(noteServer.list(context.uid(), id));

        return vo;
    }

    public PageEntity<Course> search(SearchCmd.Query query) {
        PageEntity<CourseVO> page = this.query(CourseCmd.Query.of(query.getKeyword(), query));

        PageEntity<Course> vo = new PageEntity<>();

        Map<String, List<Integer>> scoreMap = noteServer.listIn(page.getRows()
                                                                    .stream()
                                                                    .map(CourseVO::getId)
                                                                    .toList());

        vo.setTotal(page.getTotal());
        vo.setRows(page.getRows().stream().map(e -> {
            Course course = new Course();

            course.setData(e);
            course.setScore(ScoreTransfor.transfor(scoreMap.getOrDefault(e.getId(), Collections.emptyList())));

            return course;
        }).toList());
        return vo;
    }

    public List<Course> list(List<String> courses) {
        return courseRepository.list(courses);
    }
}
