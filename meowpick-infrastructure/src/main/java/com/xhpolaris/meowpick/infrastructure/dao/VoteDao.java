package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.VoteStatsCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VoteDao extends MongoRepository<VoteStatsCollection, String> {

    VoteStatsCollection findByCourseId(String courseId);
}
