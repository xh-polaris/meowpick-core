package com.xhpolaris.meowpick.domain.user.repository;

import com.xhpolaris.meowpick.domain.user.model.entity.ActionCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.ActionVO;

public interface IActionRepository {
    boolean like(String uid, String target, ActionCmd.CreateCmd cmd);

    ActionVO get(String id, String uid);
}
