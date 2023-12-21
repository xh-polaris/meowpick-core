package com.xhpolaris.meowpick.domain.course.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.course.model.entity.CourseCmd;
import com.xhpolaris.meowpick.domain.course.model.valobj.CourseVO;

public interface ICourseRepository {
    CourseVO createCourse(CourseCmd.CreateCmd cmd);

    CourseVO remove(String id);

    CourseVO updateCourse(CourseCmd.UpdateCmd cmd);

    PageEntity<CourseVO> page(CourseCmd.Query query);

    CourseVO getById(String id);
}
