package com.xhpolaris.meowpick.domain.service.search.factory;

import com.xhpolaris.meowpick.common.exceptions.BizException;
import com.xhpolaris.meowpick.domain.service.search.ISearcher;
import com.xhpolaris.meowpick.domain.model.valobj.SearchCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SearchProvider {
    private final Map<String, ISearcher> searcherMap;

    public ISearcher query(SearchCmd.Query query) {
        return Optional.ofNullable(searcherMap.get(query.getType().getValue()))
                       .orElseThrow(BizException::NotFind);
    }

}
