package com.xhpolaris.meowpick.domain.search.repository;

import java.util.List;

public interface ISearcherRepository {
    List<?> recent(String uid);
}
