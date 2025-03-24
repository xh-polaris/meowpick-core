package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.domain.model.valobj.CommentCmd;
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
        // 寻找被回复的评论
        CommentCollection db = commentDao.findByTarget(target);
        if (db == null) {
            return null;
        }

        // 将ReplyCmd类转换为Reply
        CommentCollection.Reply r = CommentMap.instance.cmd2db(cmd);
        r.setUid(uid);

        // 将二级评论添加到这条一级评论的replies数组中
        db.getReplies().add(r);
        // 保存到数据库
        commentDao.save(db);

        // XXX: 二级评论 replyTO()
        // 二级评论转换为CommentCollection类型
        CommentCollection collection = CommentMap.instance.reply2db(r);
        collection.setTarget(db.getTarget());
        collection.setFirstLevelId(db.getId());
        collection.setReplyTo(db.getUid());
        collection.setParentId(db.getId());

        // 存入二级评论到数据库
        commentDao.save(db);

        return CommentMap.instance.db2reply(r);
    }
}
