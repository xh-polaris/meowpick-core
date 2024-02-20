package com.xhpolaris.meowpick.domain.comment.service;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.comment.model.entity.CommentCmd;
import com.xhpolaris.meowpick.domain.comment.model.valobj.CommentVO;
import com.xhpolaris.meowpick.domain.comment.repository.ICommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServer {
    private final ICommentRepository commentRepository;
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
        return commentRepository.query(query);
    }

    public CommentVO get(String id) {
        return commentRepository.find(id);
    }
}
