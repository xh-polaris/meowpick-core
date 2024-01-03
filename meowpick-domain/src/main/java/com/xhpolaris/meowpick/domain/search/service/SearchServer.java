package com.xhpolaris.meowpick.domain.search.service;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.search.adapter.SearchProvider;
import com.xhpolaris.meowpick.domain.search.model.entity.SearchCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchServer {
    private final SearchProvider provider;

    public PageEntity<?> query(SearchCmd.Query query) {
        return provider.get(query.getCategory()).search(query);
    }
}
