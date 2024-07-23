package com.xhpolaris.meowpick.infrastructure.dao;

import org.springframework.data.domain.Page;
import com.xhpolaris.meowpick.infrastructure.pojo.CoursePopularityCollection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface CoursePopularityDao extends MongoRepository<CoursePopularityCollection, String> {

    // 按总热度倒序查询所有记录（总榜）
    Page<CoursePopularityCollection> findAllByOrderByPopularityDesc(Pageable pageable);

    // 按日热度倒序查询所有记录（日榜）
    Page<CoursePopularityCollection> findAllByOrderByDailyPopularityDesc(Pageable pageable);

    // 根据courseId获取课程热度
    CoursePopularityCollection findByCourseId(String courseId);
}
