package com.xhpolaris.meowpick.domain.search.adapter;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.search.model.entity.SearchCmd;

public interface ISearcher {
    PageEntity<?> search(SearchCmd.Query query);
}
