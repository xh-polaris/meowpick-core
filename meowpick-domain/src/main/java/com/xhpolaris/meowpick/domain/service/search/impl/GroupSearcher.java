package com.xhpolaris.meowpick.domain.service.search.impl;
import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.enums.TypeEn;
import com.xhpolaris.meowpick.domain.service.search.AbstractSearcher;
import com.xhpolaris.meowpick.domain.model.valobj.SearchCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component(TypeEn.Name.group)
public class GroupSearcher extends AbstractSearcher {


    @Override
    public PageEntity<?> search(SearchCmd.Query query) {
        return null;
    }
}
