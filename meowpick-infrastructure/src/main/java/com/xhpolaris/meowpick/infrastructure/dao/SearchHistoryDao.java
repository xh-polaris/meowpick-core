package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.CourseCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.SearchHistoryCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SearchHistoryDao extends MongoRepository<SearchHistoryCollection, String> {
    SearchHistoryCollection findByUidAndTextOrderByCreateAt(String uid, String text);
}
