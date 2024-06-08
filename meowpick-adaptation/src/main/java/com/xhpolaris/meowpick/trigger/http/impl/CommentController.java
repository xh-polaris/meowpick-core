package com.xhpolaris.meowpick.trigger.http.impl;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.entity.Course;
import com.xhpolaris.meowpick.domain.model.valobj.CommentCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CommentVO;
import com.xhpolaris.meowpick.domain.model.valobj.ReplyVO;
import com.xhpolaris.meowpick.domain.service.CommentServer;
import com.xhpolaris.meowpick.domain.service.Context;
import com.xhpolaris.meowpick.domain.service.CourseServer;
import com.xhpolaris.meowpick.infrastructure.rpc.PlatformSts;
import com.xhpolaris.meowpick.trigger.http.api.CommentApi;
import com.xhpolaris.meowpick.domain.service.ReplyServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController implements CommentApi {
  private final PlatformSts platformSts;

  private final CommentServer service;
  private final ReplyServer replyServer;
  private final Context context;
  private final CourseServer courseServer;

  @Override
  public CommentVO add(CommentCmd.CreateCmd cmd) {
    try{
      if (!platformSts.textCheck(cmd.getText()).getPass()) {
        return null;
      }
    } catch (Exception ex) {

    }
    return service.commit(cmd);
  }

  @Override
  public CommentVO del(String id) {
    return service.remove(id);
  }

  @Override
  public CommentVO update(CommentCmd.UpdateCmd cmd) {
    try{
      if (!platformSts.textCheck(cmd.getText()).getPass()) {
        return null;
      }
    } catch (Exception ex) {

    }
    return service.update(cmd);
  }

  @Override
  public PageEntity<CommentVO> query(CommentCmd.Query query) {
    PageEntity<CommentVO> queryData = service.query(query);
    if (CollectionUtils.isEmpty(queryData.getRows())) {
      return new PageEntity<>();
    }

    return queryData;
  }

  @Override
  public ReplyVO get(String id) {
    return service.get(id);
  }

  @Override
  public ReplyVO replyTo(String id, ReplyCmd.CreateCmd cmd) {
    try{
      if (!platformSts.textCheck(cmd.getText()).getPass()) {
        return null;
      }
    } catch (Exception ex) {

    }
    return replyServer.reply(id, cmd);
  }

  @Override
  public PageEntity<CommentVO> history(CommentCmd.History query) {
    if (StringUtils.isEmpty(query.getUid())) {
      query.setUid(context.uid());
    }

    PageEntity<CommentVO> queryData = service.queryUserCommentHistory(query);
    List<String> targets =
        queryData.getRows().stream().map(CommentVO::getTarget).distinct().toList();

    Map<String, CourseVO> courseMap =
        courseServer.list(targets).stream()
            .collect(Collectors.toMap(x -> x.getData().getId(), Course::getData));

    for (CommentVO vo : queryData.getRows()) {
      vo.setCourse(courseMap.get(vo.getTarget()));
    }

    return queryData;
  }
}
