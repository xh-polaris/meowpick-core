package com.xhpolaris.meowpick.domain.repository;

import com.xhpolaris.meowpick.domain.model.valobj.ReplyCmd;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyVO;

public interface IReplyRepository {
    ReplyVO reply(String uid, String firstLevelId, ReplyCmd.CreateCmd cmd);
}
