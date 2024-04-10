package com.xhpolaris.meowpick.trigger.http.impl;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.CommentCmd;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CommentVO;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyVO;
import com.xhpolaris.meowpick.domain.service.CommentServer;
import com.xhpolaris.meowpick.domain.service.Context;
import com.xhpolaris.meowpick.trigger.http.api.CommentApi;
import com.xhpolaris.meowpick.domain.service.ReplyServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController implements CommentApi {
    private final CommentServer service;
    private final ReplyServer replyServer;
    private final Context context;

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
        log.info("hello");
        return service.query(query);
    }

    @Override
    public ReplyVO get(String id) {
        return service.get(id);
    }

    @Override
    public ReplyVO replyTo(String id, ReplyCmd.CreateCmd cmd) {
        return replyServer.reply(id, cmd);
    }



    @Override
    public PageEntity<CommentVO> history(CommentCmd.History query) {
        if (StringUtils.isEmpty(query.getUid())) {
            query.setUid(context.uid());
        }
        return service.queryUserCommentHistory(query);
    }
}
