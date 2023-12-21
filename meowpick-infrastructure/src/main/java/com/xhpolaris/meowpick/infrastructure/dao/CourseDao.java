package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseDao extends MongoRepository<Course, String> {
    List<Course> findByCategory(String category);

    List<Course> findByDepart(String depart);

    List<Course> findByTeacher(String teacher);

    List<Course> findByCampus(String campus);

    List<Course> findByPoint(String point);
}
