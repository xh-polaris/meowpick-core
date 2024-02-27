package com.xhpolaris.meowpick.domain.search.adapter.factory;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.search.adapter.ISearcher;
import com.xhpolaris.meowpick.domain.search.model.entity.SearchCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SearchProvider {
    private final Map<String, ISearcher> searcherMap;

    public PageEntity<?> query(SearchCmd.Query query) {
        ISearcher ins = searcherMap.get(query.getType().getValue());
        if (ins != null) {
            return ins.search(query);
        }

        return new PageEntity<>();
    }

}
