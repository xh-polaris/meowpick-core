package com.xhpolaris.meowpick.infrastructure.mapstruct;

import com.xhpolaris.meowpick.domain.model.valobj.VoteStatsVO;
import com.xhpolaris.meowpick.infrastructure.pojo.VoteStatsCollection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VoteMap {
    VoteMap instance = Mappers.getMapper(VoteMap.class);

    VoteStatsCollection vo2db(VoteStatsVO vo);

    VoteStatsVO db2vo(VoteStatsCollection db);
}
