package com.xhpolaris.meowpick.trigger.http;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.tag.model.entity.TagCmd;
import com.xhpolaris.meowpick.domain.tag.model.valobj.TagVO;
import com.xhpolaris.meowpick.domain.tag.service.TagServer;
import com.xhpolaris.meowpick.trigger.http.api.TagApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TagController implements TagApi {
    private final TagServer service;

    @Override
    public TagVO add(TagCmd.CreateCmd cmd) {
        return service.exec(cmd);
    }

    @Override
    public TagVO del(String id) {
        return service.remove(id);
    }

    @Override
    public TagVO update(TagCmd.UpdateCmd cmd) {
        return service.exec(cmd);
    }

    @Override
    public PageEntity<TagVO> query(TagCmd.Query query) {
        return service.query(query);
    }

    @Override
    public TagVO get(String id) {
        return service.findById(id);
    }
}
