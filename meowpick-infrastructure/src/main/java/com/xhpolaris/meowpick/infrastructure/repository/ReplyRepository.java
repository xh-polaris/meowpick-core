package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.domain.model.valobj.ReplyCmd;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyVO;
import com.xhpolaris.meowpick.domain.repository.IReplyRepository;
import com.xhpolaris.meowpick.infrastructure.dao.CommentDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CommentMap;
import com.xhpolaris.meowpick.infrastructure.pojo.CommentCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReplyRepository implements IReplyRepository {
    private final CommentDao commentDao;

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
