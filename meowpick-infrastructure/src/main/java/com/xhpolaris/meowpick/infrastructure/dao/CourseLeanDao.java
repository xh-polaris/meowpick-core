package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.CourseLearnCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CourseLeanDao extends MongoRepository<CourseLearnCollection, String> {

    @Query("""
            {type: ?0, course: ?1, uid:  ?2}
            """)
    CourseLearnCollection findByType(String type, String course, String uid);

    List<CourseLearnCollection> findAllByActiveIsTrueAndCourse(String course);

}
