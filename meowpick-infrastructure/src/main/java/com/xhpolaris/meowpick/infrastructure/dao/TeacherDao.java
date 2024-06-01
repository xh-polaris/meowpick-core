package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.TeacherCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TeacherDao extends MongoRepository<TeacherCollection, String> {
    List<TeacherCollection> findAllByIdIn(List<String> id);
    Page<TeacherCollection> queryAllByNameLike(String name, Pageable pageable);
}
