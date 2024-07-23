package com.xhpolaris.meowpick.domain.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.entity.Course;
import com.xhpolaris.meowpick.domain.model.valobj.CourseCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.model.valobj.SearchCmd;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ICourseRepository {
    CourseVO createCourse(CourseCmd.CreateCmd cmd);

    CourseVO remove(String id);

    CourseVO updateCourse(CourseCmd.UpdateCmd cmd);

    PageEntity<CourseVO> page(CourseCmd.Query query);

    Course getById(String id, String uid);

    List<Course> list(List<String> courses);

    List<CourseVO> suggest(String search, Integer pageNum, Integer pageSize);

    Collection<String> suggestDepart(String search, Integer pageNum, Integer pageSize);

    Collection<String> suggestCategory(String search, Integer pageNum, Integer pageSize);

    PageEntity<?> cagegory(SearchCmd.Query query);

    PageEntity<?> depart(SearchCmd.Query query);

    Map<String, List<CourseVO>> findByTeachers(List<String> list);

    List<String> categoryList();

    List<String> departList();

    List<String> campusList();
}
