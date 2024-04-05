package com.xhpolaris.meowpick.trigger.listener;

import com.xhpolaris.meowpick.common.event.SearchEvent;
import com.xhpolaris.meowpick.domain.service.SearchServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchEventListener {
    private final SearchServer service;

    @EventListener(SearchEvent.class)
    void onSearch(SearchEvent event) {
        service.note(event);
    }
}
