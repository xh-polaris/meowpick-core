package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.CourseCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.TagCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TagDao extends MongoRepository<TagCollection, String> {
}
