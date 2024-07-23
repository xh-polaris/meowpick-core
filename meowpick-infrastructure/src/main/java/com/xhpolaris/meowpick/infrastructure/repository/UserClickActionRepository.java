package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.domain.repository.IUserClickActionRepository;
import com.xhpolaris.meowpick.infrastructure.dao.UserClickActionDao;
import com.xhpolaris.meowpick.infrastructure.pojo.UserClickActionCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserClickActionRepository implements IUserClickActionRepository {

    private final UserClickActionDao userClickActionDao;

    // 记录用户点击行为
    @Override
    public void saveUserClickAction(String userId, String target) {

        // 获取用户点击行为列表
        UserClickActionCollection userClickActionCollection = Optional.ofNullable(userClickActionDao.findByUid(userId))
                .orElse(new UserClickActionCollection());

        // 添加用户点击行为
        userClickActionCollection.getClick().add(new UserClickActionCollection.Action(target, new Date()));
        userClickActionCollection.setClickCount((long) userClickActionCollection.getClick().size());
        userClickActionCollection.setUid(userId);

        // 保存用户点击行为列表
        userClickActionDao.save(userClickActionCollection);
    }


    // 获取用户点击行为
    @Override
    public Boolean getUserClickAction(String userId, String target) {

        // 获取目标课程的点击行为列表
        List<UserClickActionCollection.Action> click_list = Optional.ofNullable(userClickActionDao.findByUid(userId))
                .map(UserClickActionCollection::getClick)
                .orElse(Collections.emptyList())
                .stream()
                .toList();

        // 判断用户是否在点击行为列表中
        return click_list.stream()
                .map(UserClickActionCollection.Action::getTarget)
                .collect(Collectors.toSet())
                .contains(target);
    }
}
