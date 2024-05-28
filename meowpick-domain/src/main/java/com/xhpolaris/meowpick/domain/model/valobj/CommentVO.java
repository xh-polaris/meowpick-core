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
}
