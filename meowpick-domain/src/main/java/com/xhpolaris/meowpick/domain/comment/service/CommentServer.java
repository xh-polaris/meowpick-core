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
    private final ICommentRepository repository;
    public CommentVO commit(CommentCmd.CreateCmd cmd) {
        return repository.add(cmd);
    }

    public CommentVO remove(String id) {
        return repository.del(id);
    }

    public CommentVO update(CommentCmd.UpdateCmd cmd) {
        return repository.update(cmd);
    }

    public PageEntity<CommentVO> query(CommentCmd.Query query) {
        return repository.query(query);
    }

    public CommentVO get(String id) {
        return repository.find(id);
    }
}
