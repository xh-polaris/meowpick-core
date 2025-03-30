package com.xhpolaris.meowpick;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.CommentCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CommentVO;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyCmd;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyVO;
import com.xhpolaris.meowpick.domain.service.CommentServer;
import com.xhpolaris.meowpick.domain.service.ReplyServer;
import com.xhpolaris.meowpick.trigger.http.impl.CommentController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class TestCommentReply {

    @Autowired
    private CommentServer commentServer;

    @Autowired
    private ReplyServer replyServer;

    @Autowired
    private CommentController commentController;

    @Test
    public void testReplyServerReply() {
        String target = "a4532597666144f30267770d27d60c8a";
        ReplyCmd.CreateCmd createCmd = new ReplyCmd.CreateCmd();
        createCmd.setText("TestReply002");
        ReplyVO reply = replyServer.reply(target, createCmd);
        System.out.println(reply);
    }

    @Test
    public void testCommentServerQuery() {
        CommentCmd.Query query = new CommentCmd.Query();
        query.setId("1ef3c51361efac42050e984c31de6d21");
        // query.setFirstLevelId("3213b55961f38fd700adaa236d3c5196");
        // query.setFirstLevelId("");
        PageEntity<CommentVO> queried = commentServer.query(query);
        queried.getRows().forEach(System.out::println);
    }

    @Test
    public void testCommentControllerDel() {
        CommentCmd.CreateCmd createCmd = new CommentCmd.CreateCmd();
        createCmd.setTarget("1ef3c51361efac42050e984c31de6d21");
        createCmd.setText("TestDel2");
        CommentVO added = commentController.add(createCmd);
        System.out.println("added = " + added);

        String target = added.getId();
        ReplyCmd.CreateCmd replyCmd = new ReplyCmd.CreateCmd();
        createCmd.setText("TestReply003");
        ReplyVO reply = replyServer.reply(target, replyCmd);
        System.out.println("reply = " + reply);

        CommentVO del = commentController.del(added.getId());
        System.out.println("del = " + del);
    }
}
