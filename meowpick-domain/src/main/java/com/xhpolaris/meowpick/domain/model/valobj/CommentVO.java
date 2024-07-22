package com.xhpolaris.meowpick.domain.model.valobj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Schema(name = "CommentVO")
public class CommentVO {
  private String id;

  private String text;
  private String uid;
  private List<ReplyVO> replies;

  private List<String> tags;
  private String target;

  private Integer reply;

  private ActionVO relation;

  private Date crateAt;

  private UserVO user;

  private CourseVO course;

  // 一级评论id，为空则表示为一级评论
  private String firstLevelId;

  // 回复的用户的id，若一级评论应该为空
  private String replyTo;

  // 回答的评论id
  private String parentId;

}
