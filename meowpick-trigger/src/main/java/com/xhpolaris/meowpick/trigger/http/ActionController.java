package com.xhpolaris.meowpick.trigger.http;

import com.xhpolaris.meowpick.api.ActionApi;
import com.xhpolaris.meowpick.common.Context;
import com.xhpolaris.meowpick.domain.user.model.entity.ActionCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.ActionVO;
import com.xhpolaris.meowpick.domain.user.service.ActionServer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ActionController implements ActionApi {
    private final ActionServer service;
    private final Context context;

    @Override
    public boolean like(String id, ActionCmd.CreateCmd cmd) {
        return service.like(id, cmd);
    }

    @Override
    public boolean $like(String id) {
        return service.like(context.getUser().getId(),id);
    }

    @Override
    public ActionVO actions(String id) {
        return service.actions(id);
    }
}
