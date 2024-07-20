package com.xhpolaris.meowpick.infrastructure.mapstruct;

import com.xhpolaris.meowpick.domain.model.valobj.CourseCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.model.valobj.PopularityCmd;
import com.xhpolaris.meowpick.domain.model.valobj.PopularityVO;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.PopularityCollection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PopularityMap {

    PopularityMap instance = Mappers.getMapper(PopularityMap.class);

    PopularityCollection query(PopularityCmd.Query query);

    PopularityCollection cmd2db(PopularityCmd.CreateCmd cmd);

    PopularityCollection cmd2db(PopularityCmd.UpdateCmd cmd);

    PopularityVO db2vo(PopularityCollection db);

}
