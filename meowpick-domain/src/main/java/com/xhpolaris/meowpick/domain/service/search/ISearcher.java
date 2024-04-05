package com.xhpolaris.meowpick.domain.service.search;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.SearchCmd;

public interface ISearcher {
    PageEntity<?> search(SearchCmd.Query query);
}
