package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.CommentCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentDao extends MongoRepository<CommentCollection, String> {
    List<CommentCollection> findAllByTargetInOrderByCrateAt(List<String> targets);
    List<CommentCollection> findAllByTargetOrderByCrateAt(String target);
    Page<CommentCollection> findAllByTargetOrderByCrateAtDesc(String target, Pageable pageable);
    Page<CommentCollection> findAllByUidOrderByCrateAt(String uid, Pageable pageable);
    CommentCollection findByTarget(String target);

    List<CommentCollection> findAllByTargetIn(List<String> ids);
}
