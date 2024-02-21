package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.domain.tag.repository.ITagRepository;
import com.xhpolaris.meowpick.infrastructure.dao.TagDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagRepository implements ITagRepository {
    private final TagDao tagDao;
}
