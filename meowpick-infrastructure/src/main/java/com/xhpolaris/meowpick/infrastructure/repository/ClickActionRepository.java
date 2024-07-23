package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.infrastructure.dao.ClickActionDao;
import com.xhpolaris.meowpick.domain.repository.IClickActionRepository;
import com.xhpolaris.meowpick.infrastructure.pojo.ClickActionCollection;
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
public class ClickActionRepository implements IClickActionRepository {

    private final ClickActionDao clickActionDao;

    // 记录课程被点击行为
    @Override
    public void saveClickAction(String userId, String target) {

            // 获取目标课程的点击行为列表
            ClickActionCollection clickActionCollection = Optional.ofNullable(clickActionDao.findByTarget(target))
                    .orElse(new ClickActionCollection());

            // 添加用户点击行为
            clickActionCollection.getClick().add(new ClickActionCollection.Action(userId, new Date()));
            clickActionCollection.setClickCount((long) clickActionCollection.getClick().size());
            clickActionCollection.setTarget(target);

            // 保存点击行为列表
            clickActionDao.save(clickActionCollection);
    }

    // 获取课程被点击行为
    @Override
    public Boolean getClickAction(String userId, String target) {

        // 获取目标课程的点击行为列表
        List<ClickActionCollection.Action> click_list = Optional.ofNullable(clickActionDao.findByTarget(target))
                .map(ClickActionCollection::getClick)
                .orElse(Collections.emptyList())
                .stream()
                .toList();

        // 判断用户是否在点击行为列表中
        return click_list.stream()
                .map(ClickActionCollection.Action::getUid)
                .collect(Collectors.toSet())
                .contains(userId);
    }
}
