package com.xhpolaris.meowpick.infrastructure.mapstruct;

import com.xhpolaris.meowpick.domain.model.valobj.CommentCmd;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CommentVO;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyVO;
import com.xhpolaris.meowpick.infrastructure.pojo.CommentCollection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMap {
    CommentMap instance = Mappers.getMapper(CommentMap.class);

    CommentVO db2vo(CommentCollection db);
    ReplyVO db2reply(CommentCollection db);
    ReplyVO db2reply(CommentCollection.Reply db);

    CommentCollection cmd2db(CommentCmd.CreateCmd cmd);
    CommentCollection cmd2db(CommentCmd.UpdateCmd cmd);
    CommentCollection.Reply cmd2db(ReplyCmd.CreateCmd cmd);
}
