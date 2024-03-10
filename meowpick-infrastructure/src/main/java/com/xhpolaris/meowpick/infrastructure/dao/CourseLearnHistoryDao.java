package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.CourseLearnHistoryCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseLearnHistoryDao extends MongoRepository<CourseLearnHistoryCollection, String> {
    CourseLearnHistoryCollection findByUidAndCourse(String uid, String course);
    List<CourseLearnHistoryCollection> findAllByCourse(String course);
    List<CourseLearnHistoryCollection> findAllByCourseIn(List<String> course_id);
}
