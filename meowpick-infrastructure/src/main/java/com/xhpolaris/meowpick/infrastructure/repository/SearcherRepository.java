package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.domain.search.repository.ISearcherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearcherRepository implements ISearcherRepository {

    @Override
    public List<?> recent(String uid) {
        return null;
    }
}
