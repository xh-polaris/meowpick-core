//package com.xhpolaris.meowpick.trigger.rpc;
//
//import com.xhpolaris.meowpick.common.Context;
//import com.xhpolaris.meowpick.common.PageEntity;
//import com.xhpolaris.meowpick.domain.comment.model.entity.CommentCmd;
//import com.xhpolaris.meowpick.domain.comment.model.valobj.CommentVO;
//import com.xhpolaris.meowpick.domain.comment.repository.ICommentRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class CommentRpc implements ICommentRepository {
//    private final Context context;
//
//    @Override
//    public CommentVO add(CommentCmd.CreateCmd cmd) {
//
//        return null;
//    }
//
//    @Override
//    public CommentVO del(String id) {
//        if (valid(id)) {
//            return null;
//        }
//        return null;
//    }
//
//    @Override
//    public CommentVO update(CommentCmd.UpdateCmd cmd) {
//        if (valid(cmd.getId())) {
//            return null;
//        }
//
//        return null;
//    }
//
//    @Override
//    public PageEntity<CommentVO> query(CommentCmd.Query query) {
//        return null;
//    }
//
//    @Override
//    public CommentVO find(String id) {
//        return null;
//    }
//
//    private boolean valid(String id) {
//        CommentVO vo = find(id);
//        if (vo == null) {
//            return true;
//        }
//        if (vo.getUid() != context.uid()) {
//            return true;
//        }
//        return false;
//    }
//}
