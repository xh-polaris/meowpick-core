package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.domain.service.Context;
import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.CommentCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CommentVO;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyVO;
import com.xhpolaris.meowpick.domain.repository.ICommentRepository;
import com.xhpolaris.meowpick.infrastructure.dao.CommentDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CommentMap;
import com.xhpolaris.meowpick.infrastructure.pojo.CommentCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentRepository implements ICommentRepository {
    private final CommentDao commentDao;
    private final Context    context;

    @Override
    public CommentVO add(CommentCmd.CreateCmd cmd) {
        CommentCollection db = CommentMap.instance.cmd2db(cmd);
        db.setUid(context.uid());
        commentDao.save(db);
        return CommentMap.instance.db2vo(db);
    }

    @Override
    public CommentVO del(String id) {
        CommentCollection db = commentDao.findById(id).orElse(null);
        if (db == null) {
            return null;
        }

        commentDao.deleteById(id);

        return CommentMap.instance.db2vo(db);
    }

    @Override
    public CommentVO update(CommentCmd.UpdateCmd cmd) {
        CommentCollection db = commentDao.findById(cmd.getId()).orElse(null);
        if (db == null) {
            return null;
        }

        commentDao.save(CommentMap.instance.cmd2db(cmd));

        return CommentMap.instance.db2vo(db);
    }

    @Override
    public PageEntity<CommentVO> query(CommentCmd.Query query) {
        Page<CommentCollection> page = commentDao.findAllByTargetOrderByCrateAt(query.getId(),
                PageRequest.of(query.getPage(),
                        query.getSize()
                              ));

        return BasicRepository.page(page, c -> {
            CommentVO vo = CommentMap.instance.db2vo(c);

            vo.setReply(Optional.ofNullable(c.getReplies())
                                .orElse(Collections.emptyList())
                                .size());

            return vo;
        });
    }

    @Override
    public ReplyVO find(String id) {
        CommentCollection db = commentDao.findById(id).orElse(null);
        if (db == null) {
            return null;
        }

        ReplyVO vo = CommentMap.instance.db2reply(db);
        vo.setReplies(db.getReplies()
                        .stream()
                        .map(CommentMap.instance::db2reply)
                        .toList());

        return vo;
    }

    @Override
    public PageEntity<CommentVO> queryUserComment(CommentCmd.History query) {
        return BasicRepository.page(
                commentDao.findAllByUidOrderByCrateAt(query.getUid(),
                        PageRequest.of(query.getPage(), query.getSize())),
                CommentMap.instance::db2vo);
    }
}
