package com.xhpolaris.meowpick.domain.repository;

import com.xhpolaris.meowpick.domain.model.valobj.LoginCmd;

public interface IUserThirdLoginRepository {
    void registry(String uid, LoginCmd.Query query);

    String getUid(LoginCmd.Query query);
}
