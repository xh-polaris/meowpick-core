package com.xhpolaris.meowpick.domain.service;

import com.xhpolaris.meowpick.domain.Context;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyCmd;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyVO;
import com.xhpolaris.meowpick.domain.repository.IReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReplyServer {
    private final IReplyRepository replyRepository;
    private final Context          context;

    public ReplyVO reply(String id, ReplyCmd.CreateCmd cmd) {
        return replyRepository.reply(context.uid(), id, cmd);
    }
}
