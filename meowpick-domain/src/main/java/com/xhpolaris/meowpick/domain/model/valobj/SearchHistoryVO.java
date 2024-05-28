package com.xhpolaris.meowpick.domain.model.valobj;

import lombok.Data;

import java.util.Date;

@Data
public class SearchHistoryVO {
  private String id;
  private String text;
  private Date createAt;
}
