package com.xhpolaris.meowpick.domain.search.adapter.impl;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.enums.TypeEn;
import com.xhpolaris.meowpick.domain.search.adapter.AbstractSearcher;
import com.xhpolaris.meowpick.domain.search.model.entity.SearchCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component(TypeEn.Name.user)
public class UserSearcher extends AbstractSearcher {


    @Override
    public PageEntity<?> search(SearchCmd.Query query) {
        return null;
    }
}
