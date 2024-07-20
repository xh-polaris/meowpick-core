package com.xhpolaris.meowpick.infrastructure.dao;

import org.springframework.data.domain.Page;
import com.xhpolaris.meowpick.infrastructure.pojo.PopularityCollection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface PopularityDao extends MongoRepository<PopularityCollection, String> {

    // 按热度倒序查询所有记录
    Page<PopularityCollection> findAllByDeletedIsFalseOrderByPopularityDesc(Pageable pageable);

    // 删除1个月之前的记录
    void deleteAllByCreateAtBefore(Date createAt);

    // 查询搜索记录
    PopularityCollection findByText(String text);
}
