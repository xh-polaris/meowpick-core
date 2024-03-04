package com.xhpolaris.meowpick.domain.user.repository;

import com.xhpolaris.meowpick.domain.user.model.entity.LoginCmd;

public interface IUserThirdLoginRepository {
    void registry(String uid, LoginCmd.Query query);

    String getUid(LoginCmd.Query query);
}
