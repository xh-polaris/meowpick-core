package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.SearchHistoryCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SearchHistoryDao extends MongoRepository<SearchHistoryCollection, String> {
    SearchHistoryCollection findByUidAndText(String uid, String text);
    Page<SearchHistoryCollection> findAllByUidOrderByCreateAtDesc(String uid, Pageable pageable);
}
