package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.TeacherCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeacherDao extends MongoRepository<TeacherCollection, String> {
}
