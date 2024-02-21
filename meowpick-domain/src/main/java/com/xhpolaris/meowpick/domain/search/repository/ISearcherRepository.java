package com.xhpolaris.meowpick.domain.search.repository;

import com.xhpolaris.meowpick.common.event.SearchEvent;
import com.xhpolaris.meowpick.domain.search.model.valobj.SearchHistoryVO;

import java.util.List;

public interface ISearcherRepository {
    List<SearchHistoryVO> recent(String uid);

    void note(SearchEvent event);
}
