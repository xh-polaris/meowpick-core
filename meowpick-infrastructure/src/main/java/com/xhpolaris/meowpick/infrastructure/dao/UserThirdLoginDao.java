package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.UserThirdCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserThirdLoginDao extends MongoRepository<UserThirdCollection, String> {
    UserThirdCollection findByTypeAndTokenAndBindIsTrue(String type, String token);
}