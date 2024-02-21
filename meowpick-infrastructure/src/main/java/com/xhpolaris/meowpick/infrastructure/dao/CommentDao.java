package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.CommentCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentDao extends MongoRepository<CommentCollection, String> {
    Page<CommentCollection> findAllByTargetOrderByCrateAt(String target, Pageable pageable);
    CommentCollection findByTarget(String target);
}
