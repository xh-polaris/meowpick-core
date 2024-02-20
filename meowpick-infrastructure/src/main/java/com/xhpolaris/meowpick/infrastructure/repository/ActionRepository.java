package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.domain.user.model.entity.ActionCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.ActionVO;
import com.xhpolaris.meowpick.domain.user.repository.IActionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionRepository implements IActionRepository {

    @Override
    public boolean like(String uid, String target, ActionCmd.CreateCmd cmd) {
        return false;
    }

    @Override
    public ActionVO get(String id) {
        return null;
    }
}
