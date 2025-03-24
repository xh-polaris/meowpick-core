package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.CommentCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
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

    List<CommentCollection> findAllByUpdateAtAfter(Date date);

    // 计算指定target的评论数
    Long countAllByTarget(String target);

    // 计算近1天指定target的评论数
    Long countAllByTargetAndUpdateAtAfter(String target, Date date);

    // 查找指定target的评论
    List<CommentCollection> findAllIdByTarget(String target);

    Integer countByFirstLevelId(String firstLevelId);

    // XXX: 根据firstLevelId查找所有二级评论
    List<CommentCollection> findAllByFirstLevelId(String id);
}
