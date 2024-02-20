package com.xhpolaris.meowpick.infrastructure.repository;

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
public class ReplyRepository implements IReplyRepository {

    @Override
    public List<ReplyVO> list(String id) {
        return null;
    }

    @Override
    public ReplyVO reply(String uid, String target, ReplyCmd.CreateCmd cmd) {
        return null;
    }
}
