package com.xhpolaris.meowpick.trigger.http;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.comment.model.entity.CommentCmd;
import com.xhpolaris.meowpick.domain.comment.model.valobj.CommentVO;
import com.xhpolaris.meowpick.domain.comment.service.CommentServer;
import com.xhpolaris.meowpick.trigger.http.api.CommentApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController implements CommentApi {
    private final CommentServer service;

    @Override
    public CommentVO add(CommentCmd.CreateCmd cmd) {
        return service.commit(cmd);
    }

    @Override
    public CommentVO del(String id) {
        return service.remove(id);
    }

    @Override
    public CommentVO update(CommentCmd.UpdateCmd cmd) {
        return service.update(cmd);
    }

    @Override
    public PageEntity<CommentVO> query(CommentCmd.Query query) {
        return service.query(query);
    }

    @Override
    public CommentVO get(String id) {
        return service.get(id);
    }
}
