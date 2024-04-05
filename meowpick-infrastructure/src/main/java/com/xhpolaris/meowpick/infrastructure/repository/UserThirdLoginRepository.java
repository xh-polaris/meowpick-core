package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.exceptions.BizException;
import com.xhpolaris.meowpick.domain.user.model.entity.LoginCmd;
import com.xhpolaris.meowpick.domain.user.repository.IUserThirdLoginRepository;
import com.xhpolaris.meowpick.infrastructure.dao.UserThirdLoginDao;
import com.xhpolaris.meowpick.infrastructure.pojo.UserThirdCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserThirdLoginRepository implements IUserThirdLoginRepository {
    private final UserThirdLoginDao dao;

    @Override
    public void registry(String uid, LoginCmd.Query query) {
        if (StringUtils.hasText(query.getToken())) {
            UserThirdCollection db = new UserThirdCollection();
            db.setType(query.getType().getValue());
            db.setToken(query.getToken());
            db.setUserId(uid);

            dao.save(db);
        }
    }

    @Override
    public String getUid(LoginCmd.Query query) {
        return Optional.ofNullable(dao.findByTypeAndTokenAndBindIsTrue(query.getType().getValue(), query.getToken()))
                       .map(UserThirdCollection::getUserId)
                       .orElseThrow(BizException::NotFind);
    }
}
