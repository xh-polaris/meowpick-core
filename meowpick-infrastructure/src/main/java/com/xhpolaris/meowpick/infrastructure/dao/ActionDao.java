package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.ActionCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActionDao extends MongoRepository<ActionCollection, String> {
    ActionCollection findByTarget(String target);
}
