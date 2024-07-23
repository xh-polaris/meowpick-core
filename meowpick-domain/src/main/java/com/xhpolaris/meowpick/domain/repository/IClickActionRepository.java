package com.xhpolaris.meowpick.domain.repository;

public interface IClickActionRepository {

    // 保存点击行为
    void saveClickAction(String userId, String target);

    // 获取点击行为
    Boolean getClickAction(String userId, String target);
}
