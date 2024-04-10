package com.xhpolaris.meowpick.domain.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.utils.ScoreTransfor;
import com.xhpolaris.meowpick.domain.model.valobj.CommentCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CommentVO;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyVO;

import java.util.List;
import java.util.Map;

public interface ICommentRepository {
    CommentVO add(CommentCmd.CreateCmd cmd);

    CommentVO del(String id);

    CommentVO update(CommentCmd.UpdateCmd cmd);

    PageEntity<CommentVO> query(CommentCmd.Query query);

    ReplyVO find(String id);

    PageEntity<CommentVO> queryUserComment(CommentCmd.History query);

    ScoreTransfor.Score score(String id);

    Map<String, List<Integer>> scoreIn(List<String> list);
}
