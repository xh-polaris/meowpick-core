package com.xhpolaris.meowpick.domain.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.utils.ScoreTransfor;
import com.xhpolaris.meowpick.domain.model.entity.Course;
import com.xhpolaris.meowpick.domain.model.valobj.CourseCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;

import java.util.List;
import java.util.Map;

public interface ICourseRepository {
    CourseVO createCourse(CourseCmd.CreateCmd cmd);

    CourseVO remove(String id);

    CourseVO updateCourse(CourseCmd.UpdateCmd cmd);

    PageEntity<CourseVO> page(CourseCmd.Query query);

    Course getById(String id, String uid);

    List<Course> list(List<String> courses);
}
