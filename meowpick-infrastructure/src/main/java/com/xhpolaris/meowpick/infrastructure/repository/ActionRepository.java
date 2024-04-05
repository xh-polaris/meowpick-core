package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.domain.model.valobj.ActionCmd;
import com.xhpolaris.meowpick.domain.model.valobj.ActionVO;
import com.xhpolaris.meowpick.domain.repository.IActionRepository;
import com.xhpolaris.meowpick.infrastructure.dao.ActionDao;
import com.xhpolaris.meowpick.infrastructure.dao.UserActionDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.ActionMap;
import com.xhpolaris.meowpick.infrastructure.pojo.ActionCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.UserActionCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionRepository implements IActionRepository {
    private final ActionDao actionDao;
    private final UserActionDao userActionDao;

    @Override
    public boolean like(String uid, String target, ActionCmd.CreateCmd cmd) {
        UserActionCollection user = userActionDao.findByUid(uid);
        ActionCollection action = actionDao.findByTarget(target);

        if (user == null) {
            user = new UserActionCollection();
            user.setUid(uid);
            user.setLike(new ArrayList<>());
        }

        if (user.getLike()
                .stream()
                .map(UserActionCollection.Action::getTarget)
                .collect(Collectors.toSet())
                .contains(target)) {
            return doCancel(uid, target, user, action);
        }

        if (action == null) {
            action = new ActionCollection();
            action.setTarget(target);
            action.setLike(new ArrayList<>());
        }

        ActionCollection.Action action_like = new ActionCollection.Action();
        UserActionCollection.Action user_like = new UserActionCollection.Action();

        action_like.setUid(uid);
        action_like.setEmoji(cmd.getEmoji());
        user_like.setTarget(target);
        user_like.setEmoji(cmd.getEmoji());

        action.getLike().add(action_like);
        user.getLike().add(user_like);

        userActionDao.save(user);
        actionDao.save(action);

        return true;
    }

    private boolean doCancel(String uid,
                             String target,
                             UserActionCollection user,
                             ActionCollection action) {
        user.setLike(user.getLike()
                         .stream()
                         .filter(i -> !i.getTarget().equals(target))
                         .toList());
        action.setLike(action.getLike()
                             .stream()
                             .filter(i -> !i.getUid().equals(uid))
                             .toList());

        userActionDao.save(user);
        actionDao.save(action);

        return true;
    }

    @Override
    public ActionVO get(String id, String uid) {
        ActionVO vo = new ActionVO();

        List<ActionVO.Action> like_list = Optional.ofNullable(actionDao.findByTarget(id))
                                                  .map(ActionCollection::getLike)
                                                  .orElse(Collections.emptyList())
                                                  .stream()
                                                  .map(ActionMap.instance::db2vo)
                                                  .toList();
        vo.setLikes(like_list);
        vo.setLike(like_list.stream()
                             .map(ActionVO.Action::getUid)
                             .collect(Collectors.toSet())
                             .contains(uid));
        vo.setLike_cnt(like_list.size());

        return vo;
    }
}
