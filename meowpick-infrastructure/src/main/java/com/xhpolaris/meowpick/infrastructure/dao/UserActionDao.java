package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.CourseCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.UserActionCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserActionDao extends MongoRepository<UserActionCollection, String> {
    UserActionCollection findByUid(String uid);
}
