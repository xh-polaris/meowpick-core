package com.xhpolaris.meowpick.domain.repository;

import com.xhpolaris.meowpick.domain.model.valobj.ActionCmd;
import com.xhpolaris.meowpick.domain.model.valobj.ActionVO;

public interface IActionRepository {
    boolean like(String uid, String target, ActionCmd.CreateCmd cmd);

    ActionVO get(String id, String uid);
}
