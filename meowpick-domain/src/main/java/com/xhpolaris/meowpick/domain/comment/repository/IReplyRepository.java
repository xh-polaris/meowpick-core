package com.xhpolaris.meowpick.domain.comment.repository;

import com.xhpolaris.meowpick.domain.comment.model.entity.ReplyCmd;
import com.xhpolaris.meowpick.domain.comment.model.valobj.ReplyVO;

import java.util.List;

public interface IReplyRepository {
    @Deprecated
    List<ReplyVO> list(String id);

    ReplyVO reply(String uid, String target, ReplyCmd.CreateCmd cmd);
}
