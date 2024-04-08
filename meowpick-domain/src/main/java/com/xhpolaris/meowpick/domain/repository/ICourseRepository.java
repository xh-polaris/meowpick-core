package com.xhpolaris.meowpick.domain.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.enums.CourseNoteEn;
import com.xhpolaris.meowpick.domain.model.entity.Course;
import com.xhpolaris.meowpick.domain.model.valobj.CourseCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseNoteCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseNote;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;

import java.util.List;
import java.util.Map;

public interface ICourseRepository {
    CourseVO createCourse(CourseCmd.CreateCmd cmd);

    CourseVO remove(String id);

    CourseVO updateCourse(CourseCmd.UpdateCmd cmd);

    PageEntity<CourseVO> page(CourseCmd.Query query);

    Course getById(String id, String uid);

    CourseNote history(String uid, String course);

    boolean note(String uid, String course, CourseNoteCmd.CreateCmd cmd, CourseNoteEn en);

    List<Integer> courseScoreList(String id);

    CourseNoteEn currentState(String uid, String course);

    Map<String, List<Integer>> courseScoreListIn(List<String> ids);

    List<Course> list(List<String> courses);
}
