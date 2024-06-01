package com.xhpolaris.meowpick.domain.service.search.impl;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.enums.TypeEn;
import com.xhpolaris.meowpick.domain.service.search.AbstractSearcher;
import com.xhpolaris.meowpick.domain.model.valobj.SearchCmd;
import com.xhpolaris.meowpick.domain.service.TeacherServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component(TypeEn.Name.teacher)
public class TeacherSearcher extends AbstractSearcher {
  private final TeacherServer server;

  @Override
  public PageEntity<?> search(SearchCmd.Query query) {
    return server.search(query.getKeyword(), query.getPage(), query.getSize());
  }
}
