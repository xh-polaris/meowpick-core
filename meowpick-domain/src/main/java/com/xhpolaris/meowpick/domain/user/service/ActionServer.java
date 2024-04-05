package com.xhpolaris.meowpick.domain.user.service;

import com.xhpolaris.meowpick.domain.Context;
import com.xhpolaris.meowpick.domain.user.model.entity.ActionCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.ActionVO;
import com.xhpolaris.meowpick.domain.user.repository.IActionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionServer {
    private final IActionRepository repository;
    private final Context context;

    public boolean like(String id, ActionCmd.CreateCmd cmd) {
        return repository.like(context.uid(), id, cmd);
    }

    public ActionVO relation(String id) {
        return repository.get(id, context.uid());
    }
}
