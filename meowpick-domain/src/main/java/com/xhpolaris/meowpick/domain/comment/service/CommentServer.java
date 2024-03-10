package com.xhpolaris.meowpick.domain.comment.service;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.comment.model.entity.CommentCmd;
import com.xhpolaris.meowpick.domain.comment.model.valobj.CommentVO;
import com.xhpolaris.meowpick.domain.comment.model.valobj.ReplyVO;
import com.xhpolaris.meowpick.domain.comment.repository.ICommentRepository;
import com.xhpolaris.meowpick.domain.user.repository.IUserRepository;
import com.xhpolaris.meowpick.domain.user.service.ActionServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServer {
    private final ICommentRepository commentRepository;
    private final ActionServer       actionServer;

    private final IUserRepository userRepository;

    public CommentVO commit(CommentCmd.CreateCmd cmd) {
        return commentRepository.add(cmd);
    }

    public CommentVO remove(String id) {
        return commentRepository.del(id);
    }

    public CommentVO update(CommentCmd.UpdateCmd cmd) {
        return commentRepository.update(cmd);
    }

    public PageEntity<CommentVO> query(CommentCmd.Query query) {
        PageEntity<CommentVO> page = commentRepository.query(query);
        page.getRows().forEach(vo -> {
            vo.setRelation(actionServer.relation(vo.getId()));
            vo.setUser(userRepository.getById(vo.getUid()));
        });

        return page;
    }

    public ReplyVO get(String id) {
        return commentRepository.find(id);
    }
}
