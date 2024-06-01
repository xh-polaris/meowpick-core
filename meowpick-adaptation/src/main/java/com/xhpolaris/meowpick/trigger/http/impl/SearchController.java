package com.xhpolaris.meowpick.trigger.http.impl;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.SearchCmd;
import com.xhpolaris.meowpick.domain.model.valobj.SearchHistoryVO;
import com.xhpolaris.meowpick.domain.service.SearchServer;
import com.xhpolaris.meowpick.trigger.http.api.SearchApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SearchController implements SearchApi {
  private final SearchServer searchServer;

  @Override
  public String guess() {
    return "搜索";
  }

  @Override
  public List<SearchHistoryVO> recent() {
    return searchServer.recent();
  }

  @Override
  public boolean removeRecent(String id) {
    return searchServer.recentRemove(id);
  }

  @Override
  public List<?> suggest(String search, Integer pageNum, Integer pageSize) {
    return searchServer.suggest(search, pageNum, pageSize);
  }

  @Override
  public PageEntity<?> search(SearchCmd.Query query) {
    return searchServer.query(query);
  }

  @Override
  public Integer total() {
    return searchServer.total();
  }
}
