package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.event.SearchEvent;
import com.xhpolaris.meowpick.common.properties.SearchHistoryProperties;
import com.xhpolaris.meowpick.domain.model.valobj.SearchHistoryVO;
import com.xhpolaris.meowpick.domain.repository.ISearcherRepository;
import com.xhpolaris.meowpick.infrastructure.dao.SearchHistoryDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.SearchHistoryMap;
import com.xhpolaris.meowpick.infrastructure.pojo.SearchHistoryCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearcherRepository implements ISearcherRepository {
    private final SearchHistoryDao        searchHistoryDao;
    private final SearchHistoryProperties properties;

    @Override
    public List<SearchHistoryVO> recent(String uid) {
        Page<SearchHistoryCollection> page = searchHistoryDao
                .findAllByUidOrderByCreateAtDesc(uid, PageRequest.of(0, properties.getSize()));
        return page.getContent().stream().map(SearchHistoryMap.instance::db2vo).toList();
    }

    @Override
    public void note(SearchEvent event) {
        SearchHistoryCollection history = searchHistoryDao.findByUidAndText(event.getUid(),
                event.getText());
        if (history != null) {
            history.setCount(history.getCount() + 1);

            searchHistoryDao.save(history);
            return;
        }

        SearchHistoryCollection db = SearchHistoryMap.instance.cmd2db(event);
        searchHistoryDao.save(db);
    }
}
