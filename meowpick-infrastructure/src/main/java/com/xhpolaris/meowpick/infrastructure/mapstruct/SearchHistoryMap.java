package com.xhpolaris.meowpick.infrastructure.mapstruct;

import com.xhpolaris.meowpick.common.event.SearchEvent;
import com.xhpolaris.meowpick.domain.model.valobj.SearchHistoryVO;
import com.xhpolaris.meowpick.infrastructure.pojo.SearchHistoryCollection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SearchHistoryMap {
    SearchHistoryMap instance = Mappers.getMapper(SearchHistoryMap.class);

    SearchHistoryCollection cmd2db(SearchEvent event);
    SearchHistoryVO db2vo(SearchHistoryCollection db);
}
