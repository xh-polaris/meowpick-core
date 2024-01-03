package com.xhpolaris.meowpick.domain;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.search.model.entity.SearchCmd;

public interface SearchComponent<T> {
    PageEntity<T> search(SearchCmd.Query query);
}
