package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.domain.comment.model.entity.ReplyCmd;
import com.xhpolaris.meowpick.domain.comment.model.valobj.ReplyVO;
import com.xhpolaris.meowpick.domain.comment.repository.IReplyRepository;
import com.xhpolaris.meowpick.infrastructure.dao.CommentDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CommentMap;
import com.xhpolaris.meowpick.infrastructure.pojo.CommentCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReplyRepository implements IReplyRepository {
    private final CommentDao commentDao;

    @Override
    @Deprecated
    public List<ReplyVO> list(String id) {
        return null;
    }

    @Override
    public ReplyVO reply(String uid, String target, ReplyCmd.CreateCmd cmd) {
        CommentCollection db = commentDao.findByTarget(target);
        if (db == null) {
            return null;
        }

        CommentCollection.Reply r = CommentMap.instance.cmd2db(cmd);
        r.setUid(uid);

        db.getReplies().add(r);
        commentDao.save(db);

        return CommentMap.instance.db2reply(r);
    }
}
