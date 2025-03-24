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

    Integer replyCount(String firstLevelId,String id);

    /** XXX: 返回一级评论的同时，返回其二级评论
     * 获取二级评论数组
     * @param firstLevelId 二级评论所指向的一级评论，一级评论为空
     * @param id 一级评论的id
     * @return 二级评论集合
     */
    List<ReplyVO> replies(String firstLevelId, String id);
}
