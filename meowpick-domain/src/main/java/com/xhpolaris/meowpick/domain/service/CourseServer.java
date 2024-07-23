package com.xhpolaris.meowpick.domain.service;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.utils.ScoreTransfor;
import com.xhpolaris.meowpick.domain.model.entity.Course;
import com.xhpolaris.meowpick.domain.model.valobj.CourseCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.model.valobj.SearchCmd;
import com.xhpolaris.meowpick.domain.repository.IClickActionRepository;
import com.xhpolaris.meowpick.domain.repository.ICommentRepository;
import com.xhpolaris.meowpick.domain.repository.ICourseRepository;
import com.xhpolaris.meowpick.domain.repository.IUserClickActionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServer {
    private final ICourseRepository  courseRepository;
    private final ICommentRepository commentRepository;
    private final IClickActionRepository clickActionRepository;
    private final IUserClickActionRepository userClickActionRepository;
    private final Context            context;

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

        vo.setScore(commentRepository.score(id));

        // 记录点击事件
        clickActionRepository.saveClickAction(context.uid(), id);
        userClickActionRepository.saveUserClickAction(context.uid(), id);

        return vo;
    }

    public PageEntity<Course> search(SearchCmd.Query query) {
        PageEntity<CourseVO> page = this.query(CourseCmd.Query.of(query.getKeyword(), query));

        PageEntity<Course> vo = new PageEntity<>();

        Map<String, List<Integer>> scoreMap = commentRepository.scoreIn(page.getRows()
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
        Map<String, List<Integer>> scoreMap = commentRepository.scoreIn(courses);
        List<Course>               list     = courseRepository.list(courses);
        list.forEach(course -> course.setScore(ScoreTransfor.transfor(scoreMap.getOrDefault(course.getData().getId(),
                Collections.emptyList()))));
        return list;
    }

    public List<CourseVO> suggestName(String search, Integer pageNum, Integer pageSize) {
        return courseRepository.suggest(search, pageNum, pageSize);
    }

    public Collection<String> suggestDepart(String search, Integer pageNum, Integer pageSize) {
        return courseRepository.suggestDepart(search, pageNum, pageSize);
    }

    public Collection<String> suggestCategory(String search, Integer pageNum, Integer pageSize) {
        return courseRepository.suggestCategory(search, pageNum, pageSize);
    }

    public PageEntity<?> searchDepart(SearchCmd.Query query) {
        return courseRepository.depart(query);
    }
    public PageEntity<?> searchCategory(SearchCmd.Query query) {
        return courseRepository.cagegory(query);
    }

    public List<String> categoryList() {
        return courseRepository.categoryList();
    }

}
