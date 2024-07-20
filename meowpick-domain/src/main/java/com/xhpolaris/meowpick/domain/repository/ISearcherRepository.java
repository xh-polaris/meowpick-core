package com.xhpolaris.meowpick.domain.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.event.SearchEvent;
import com.xhpolaris.meowpick.domain.model.valobj.PopularityCmd;
import com.xhpolaris.meowpick.domain.model.valobj.SearchHistoryVO;

import java.util.List;

public interface ISearcherRepository {

    PageEntity<String> guess(PopularityCmd.Query query);

    List<SearchHistoryVO> recent(String uid);

    void note(SearchEvent event);

    Integer total();

    boolean recentRemove(String id);
}
