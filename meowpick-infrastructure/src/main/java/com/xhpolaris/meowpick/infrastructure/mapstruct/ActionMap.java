package com.xhpolaris.meowpick.infrastructure.mapstruct;

import com.xhpolaris.meowpick.domain.user.model.valobj.ActionVO;
import com.xhpolaris.meowpick.infrastructure.pojo.ActionCollection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ActionMap {
    ActionMap instance = Mappers.getMapper(ActionMap.class);

    ActionVO.Action db2vo(ActionCollection.Action db);
}
