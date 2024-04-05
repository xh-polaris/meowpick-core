package com.xhpolaris.meowpick.domain.service;

import com.xhpolaris.meowpick.domain.Context;
import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.event.SearchEvent;
import com.xhpolaris.meowpick.domain.model.valobj.SearchCmd;
import com.xhpolaris.meowpick.domain.model.valobj.SearchHistoryVO;
import com.xhpolaris.meowpick.domain.repository.ISearcherRepository;
import com.xhpolaris.meowpick.domain.service.search.ISearcher;
import com.xhpolaris.meowpick.domain.service.search.factory.SearchProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchServer {
    private final SearchProvider provider;
    private final Context        context;
    private final ISearcherRepository repository;

    public PageEntity<?> query(SearchCmd.Query query) {
        context.publish(new SearchEvent(context.uid(),
                                        query.getType().getValue(),
                                        query.getKeyword()
        ));

        ISearcher searcher = provider.query(query);
        return searcher.search(query);
    }

    public List<SearchHistoryVO> recent() {
        return repository.recent(context.uid());
    }

    public void note(SearchEvent event) {
        repository.note(event);
    }
}
