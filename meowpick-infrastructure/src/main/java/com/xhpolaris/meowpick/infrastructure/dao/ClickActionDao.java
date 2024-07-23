package com.xhpolaris.meowpick.infrastructure.dao;

import com.xhpolaris.meowpick.infrastructure.pojo.ClickActionCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ClickActionDao extends MongoRepository<ClickActionCollection, String> {

    // 根据target获取点击行为
    ClickActionCollection findByTarget(String target);

    // 查找近 1 天的点击记录
    List<ClickActionCollection> findAllByUpdateAtAfter(Date date);
}
