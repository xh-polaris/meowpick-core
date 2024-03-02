package com.xhpolaris.meowpick.domain.comment.service;

import com.xhpolaris.meowpick.common.Context;
import com.xhpolaris.meowpick.domain.comment.model.entity.ReplyCmd;
import com.xhpolaris.meowpick.domain.comment.model.valobj.ReplyVO;
import com.xhpolaris.meowpick.domain.comment.repository.IReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReplyServer {
    private final IReplyRepository replyRepository;
    private final Context context;

    public List<ReplyVO> replies(String id) {
        return replyRepository.list(id);
    }

    public ReplyVO reply(String id, ReplyCmd.CreateCmd cmd) {
        return replyRepository.reply(context.uid(), id, cmd);
    }
}
