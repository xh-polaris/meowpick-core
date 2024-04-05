package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.function.Function;

public class BasicRepository<T, V> {

    public static <T, V> PageEntity<V> page(Page<T> page, Function<T, V> map) {
        PageEntity<V> vo = new PageEntity<>();

        vo.setTotal(page.getTotalElements());
        if (vo.getTotal() > 0) {
            vo.setRows(page.getContent().stream().map(map).toList());
        }

        return vo;
    }
}
