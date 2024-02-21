package com.xhpolaris.meowpick.domain.comment.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.comment.model.entity.CommentCmd;
import com.xhpolaris.meowpick.domain.comment.model.valobj.CommentVO;
import com.xhpolaris.meowpick.domain.comment.model.valobj.ReplyVO;

public interface ICommentRepository {
    CommentVO add(CommentCmd.CreateCmd cmd);

    CommentVO del(String id);

    CommentVO update(CommentCmd.UpdateCmd cmd);

    PageEntity<CommentVO> query(CommentCmd.Query query);

    ReplyVO find(String id);
}
