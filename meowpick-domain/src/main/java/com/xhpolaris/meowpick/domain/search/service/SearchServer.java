package com.xhpolaris.meowpick.domain.search.service;

import com.xhpolaris.meowpick.common.Context;
import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.event.SearchEvent;
import com.xhpolaris.meowpick.domain.search.adapter.ISearcher;
import com.xhpolaris.meowpick.domain.search.adapter.factory.SearchProvider;
import com.xhpolaris.meowpick.domain.search.model.entity.SearchCmd;
import com.xhpolaris.meowpick.domain.search.model.valobj.SearchHistoryVO;
import com.xhpolaris.meowpick.domain.search.repository.ISearcherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchServer {
    private final SearchProvider provider;
    private final Context context;
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
