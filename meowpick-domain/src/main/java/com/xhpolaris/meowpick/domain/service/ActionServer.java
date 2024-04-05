package com.xhpolaris.meowpick.domain.service;

import com.xhpolaris.meowpick.domain.model.valobj.ActionCmd;
import com.xhpolaris.meowpick.domain.model.valobj.ActionVO;
import com.xhpolaris.meowpick.domain.repository.IActionRepository;
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
