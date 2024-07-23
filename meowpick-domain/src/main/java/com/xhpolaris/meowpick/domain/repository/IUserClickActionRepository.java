package com.xhpolaris.meowpick.domain.repository;

public interface IUserClickActionRepository {

    // 保存用户点击行为
    void saveUserClickAction(String userId, String target);

    // 获取用户点击行为
    Boolean getUserClickAction(String userId, String target);
}
