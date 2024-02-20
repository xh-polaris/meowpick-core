package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.comment.model.entity.CommentCmd;
import com.xhpolaris.meowpick.domain.comment.model.valobj.CommentVO;
import com.xhpolaris.meowpick.domain.comment.repository.ICommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentRepository implements ICommentRepository {
    @Override
    public CommentVO add(CommentCmd.CreateCmd cmd) {
        return null;
    }

    @Override
    public CommentVO del(String id) {
        return null;
    }

    @Override
    public CommentVO update(CommentCmd.UpdateCmd cmd) {
        return null;
    }

    @Override
    public PageEntity<CommentVO> query(CommentCmd.Query query) {
        return null;
    }

    @Override
    public CommentVO find(String id) {
        return null;
    }
}
