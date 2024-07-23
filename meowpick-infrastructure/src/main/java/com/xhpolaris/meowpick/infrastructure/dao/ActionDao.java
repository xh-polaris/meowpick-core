package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.ActionCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface ActionDao extends MongoRepository<ActionCollection, String> {
    ActionCollection findByTarget(String target);

    // 和CommentCollection进行多表联查，查找指定课程id的点赞记录
    List<ActionCollection> findAllByTargetIn(List<String> ids);
}
