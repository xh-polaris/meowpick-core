package com.xhpolaris.meowpick.trigger.http.impl;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.search.model.entity.SearchCmd;
import com.xhpolaris.meowpick.domain.search.model.valobj.SearchHistoryVO;
import com.xhpolaris.meowpick.domain.search.service.SearchServer;
import com.xhpolaris.meowpick.trigger.http.api.SearchApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController implements SearchApi {
    private final SearchServer searchServer;

    @Override
    public String guess() {
        return "搜索";
    }

    @Override
    public List<SearchHistoryVO> recent() {
        return searchServer.recent();
    }

    @Override
    public List<?> suggest() {
        return List.of();
    }

    @Override
    public PageEntity<?> search(SearchCmd.Query query) {
        return searchServer.query(query);
    }
}
