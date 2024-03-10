package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.domain.course.model.entity.CourseCmd;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CourseDao extends MongoRepository<CourseCollection, String> {
    @Query("""
            {
                $and: [
                    { 'name': { $regex: ?#{[0]}, $options: 'i' } },
                    { 'category': ?#{[1]} },
                    { 'department': ?#{[2]} },
                    { 'depart': ?#{[3]} },
                    { 'point': ?#{[4]} },
                    { 'teachers': { $all: ?#{[5]} } },
                    { 'campuses': { $in: ?#{[6]} } }
                ]
            }
            """)
    Page<CourseCollection> query(CourseCmd.Query query, Pageable pageable);


}
