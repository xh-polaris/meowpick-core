package com.xhpolaris.meowpick.domain.search.adapter.impl;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.enums.TypeEn;
import com.xhpolaris.meowpick.domain.search.adapter.AbstractSearcher;
import com.xhpolaris.meowpick.domain.search.model.entity.SearchCmd;
import com.xhpolaris.meowpick.domain.teacher.service.TeacherServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component(TypeEn.Name.teacher)
public class TeacherSearcher extends AbstractSearcher {
    private final TeacherServer server;

    @Override
    public PageEntity<?> search(SearchCmd.Query query) {
        return null;
    }
}
