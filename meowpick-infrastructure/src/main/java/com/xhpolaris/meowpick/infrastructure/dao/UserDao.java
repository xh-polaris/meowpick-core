package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.UserCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDao extends MongoRepository<UserCollection, String> {
    UserCollection findByPhone(String phone);
}
