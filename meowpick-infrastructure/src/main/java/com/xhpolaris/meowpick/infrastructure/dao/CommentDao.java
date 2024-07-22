package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.CommentCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CommentDao extends MongoRepository<CommentCollection, String> {

    @Query("{ 'target': { $eq: ?0 }, 'firstLevelId': { $eq: ?1 } }")
    Page<CommentCollection> findAllByTargetAndFirstLevelIdOrderByCrateAt(String targets, String firstLevelId,Pageable pageable);

    List<CommentCollection> findAllByTargetInOrderByCrateAt(List<String> targets);
    List<CommentCollection> findAllByTargetOrderByCrateAt(String target);
    Page<CommentCollection> findAllByTargetOrderByCrateAtDesc(String target, Pageable pageable);
    @Query("{$or: [{'uid': ?0}, {'uid': ?1}]}")
    Page<CommentCollection> findAllByUidOrderByCrateAt(String uid,String openId, Pageable pageable);

    Page<CommentCollection> findAllByUidOrderByCrateAt(String uid, Pageable pageable);
    CommentCollection findByTarget(String target);

    List<CommentCollection> findAllByTargetIn(List<String> ids);
    Integer countByFirstLevelId(String firstLevelId);
}
