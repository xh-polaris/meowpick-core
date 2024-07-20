package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.SearchHistoryCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface SearchHistoryDao extends MongoRepository<SearchHistoryCollection, String> {
  SearchHistoryCollection findByUidAndText(String uid, String text);

  SearchHistoryCollection findByUidAndId(String uid, String id);

  Page<SearchHistoryCollection> findAllByUidAndDeletedIsFalseOrderByCreateAtDesc(
      String uid, Pageable pageable);

  // 查询未被删除的搜索记录并以创建时间倒序排列
  List<SearchHistoryCollection> findAllByCreateAtGreaterThanAndDeletedIsFalseOrderByCreateAtDesc(Date createAt);

}
