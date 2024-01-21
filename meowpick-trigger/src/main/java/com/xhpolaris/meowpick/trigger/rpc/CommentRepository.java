package com.xhpolaris.meowpick.trigger.rpc;

import com.xhpolaris.idlgen.platform.comment.CommentServiceGrpc;
import com.xhpolaris.idlgen.platform.comment.CreateCommentReq;
import com.xhpolaris.idlgen.platform.comment.CreateCommentResp;
import com.xhpolaris.idlgen.platform.comment.DeleteCommentByIdReq;
import com.xhpolaris.idlgen.platform.comment.DeleteCommentByIdResp;
import com.xhpolaris.idlgen.platform.comment.ListCommentByParentReq;
import com.xhpolaris.idlgen.platform.comment.ListCommentByParentResp;
import com.xhpolaris.idlgen.platform.comment.RetrieveCommentByIdReq;
import com.xhpolaris.idlgen.platform.comment.RetrieveCommentByIdResp;
import com.xhpolaris.idlgen.platform.comment.UpdateCommentReq;
import com.xhpolaris.idlgen.platform.comment.UpdateCommentResp;
import com.xhpolaris.meowpick.common.Context;
import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.comment.model.entity.CommentCmd;
import com.xhpolaris.meowpick.domain.comment.model.valobj.CommentVO;
import com.xhpolaris.meowpick.domain.comment.repository.ICommentRepository;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentRepository implements ICommentRepository {
    private final Context context;

    @GrpcClient("meowchat-content")
    private CommentServiceGrpc.CommentServiceBlockingStub grpc;

    @Override
    public CommentVO add(CommentCmd.CreateCmd cmd) {
        CreateCommentResp resp = grpc.createComment(CreateCommentReq.newBuilder()
                                                                    .setAuthorId(context.getUser()
                                                                                        .getId())

                                                                    .setReplyTo(cmd.getTarget())
                                                                    .setText(cmd.getText())
                                                                    .build());

        return null;
    }

    @Override
    public CommentVO del(String id) {
        if (valid(id)) {
            return null;
        }


        DeleteCommentByIdResp resp = grpc.deleteComment(DeleteCommentByIdReq.newBuilder()
                                                                            .setId(id)
                                                                            .build());
        return null;
    }

    @Override
    public CommentVO update(CommentCmd.UpdateCmd cmd) {
        if (valid(cmd.getId())) {
            return null;
        }
        UpdateCommentResp resp = grpc.updateComment(UpdateCommentReq.newBuilder()
                                                                    .setId(cmd.getId())
                                                                    .setText(cmd.getText())
                                                                    .build());
        return null;
    }

    @Override
    public PageEntity<CommentVO> query(CommentCmd.Query query) {
        ListCommentByParentResp resp = grpc.listCommentByParent(ListCommentByParentReq.newBuilder()
                                                                                      .build());
        return null;
    }

    @Override
    public CommentVO find(String id) {
        RetrieveCommentByIdResp resp = grpc.retrieveCommentById(RetrieveCommentByIdReq.newBuilder()
                                                                                      .setId(id)
                                                                                      .build());
        return null;
    }

    private boolean valid(String id) {
        CommentVO vo = find(id);
        if (vo == null) {
            return true;
        }
        if (vo.getUid() != context.getUser()
                                  .getId()) {
            return true;
        }
        return false;
    }
}
