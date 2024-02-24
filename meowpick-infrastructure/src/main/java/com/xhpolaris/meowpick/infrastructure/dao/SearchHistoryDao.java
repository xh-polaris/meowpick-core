package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.SearchHistoryCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SearchHistoryDao extends MongoRepository<SearchHistoryCollection, String> {
    SearchHistoryCollection findByUidAndText(String uid, String text);
}
