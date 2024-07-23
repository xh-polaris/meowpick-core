package com.xhpolaris.meowpick.infrastructure.dao;

import ch.qos.logback.classic.spi.LoggingEventVO;
import com.xhpolaris.meowpick.domain.model.valobj.CourseCmd;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface CourseDao extends MongoRepository<CourseCollection, String> {
  @Query(
      """
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

  List<CourseCollection> findByTeachersIn(List<String> teachers);

  Page<CourseCollection> queryAllByNameLike(String name, Pageable pageable);
  Page<CourseCollection> queryAllByName(String name, Pageable pageable);

  Page<CourseCollection> queryAllByDepartmentLike(String name, Pageable pageable);
  Page<CourseCollection> queryAllByDepartment(String name, Pageable pageable);

  Page<CourseCollection> queryAllByCategoryLike(String name, Pageable pageable);
  Page<CourseCollection> queryAllByCategory(String name, Pageable pageable);

}
