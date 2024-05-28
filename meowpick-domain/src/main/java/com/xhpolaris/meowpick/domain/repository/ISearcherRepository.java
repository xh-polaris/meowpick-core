package com.xhpolaris.meowpick.domain.repository;

import com.xhpolaris.meowpick.common.event.SearchEvent;
import com.xhpolaris.meowpick.domain.model.valobj.SearchHistoryVO;

import java.util.List;

public interface ISearcherRepository {
    List<SearchHistoryVO> recent(String uid);

    void note(SearchEvent event);

    Integer total();

    boolean recentRemove(String id);
}
