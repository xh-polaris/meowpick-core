package com.xhpolaris.meowpick.domain.comment.repository;


import com.xhpolaris.meowpick.domain.model.valobj.ReplyCmd;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyVO;

import java.util.List;

public interface IReplyRepository {
    ReplyVO reply(String uid, String target, ReplyCmd.CreateCmd cmd);
}
