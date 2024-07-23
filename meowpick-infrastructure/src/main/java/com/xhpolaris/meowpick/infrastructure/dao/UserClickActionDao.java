package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.UserClickActionCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserClickActionDao extends MongoRepository<UserClickActionCollection, String> {

    // 根据uid获取用户点击行为
    UserClickActionCollection findByUid(String uid);
}
