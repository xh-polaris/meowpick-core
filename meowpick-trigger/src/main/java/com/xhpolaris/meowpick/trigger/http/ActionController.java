package com.xhpolaris.meowpick.trigger.http;

import com.xhpolaris.meowpick.api.ActionApi;
import com.xhpolaris.meowpick.domain.user.model.entity.ActionCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.ActionVO;
import com.xhpolaris.meowpick.domain.user.service.ActionServer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ActionController implements ActionApi {
    private final ActionServer service;

    @Override
    public boolean like(String id, ActionCmd.CreateCmd cmd) {
        return service.like(id, cmd);
    }

    @Override
    public ActionVO actions(String id) {
        return service.actions(id);
    }
}
