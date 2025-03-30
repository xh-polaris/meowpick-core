package com.xhpolaris.meowpick.infrastructure.mapstruct;

import com.xhpolaris.meowpick.domain.model.valobj.CourseCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseCollection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMap {
    CourseMap instance = Mappers.getMapper(CourseMap.class);

    CourseCollection query(CourseCmd.Query query);
    CourseCollection cmd2db(CourseCmd.CreateCmd cmd);

    CourseCollection cmd2db(CourseCmd.UpdateCmd cmd);

    CourseVO db2vo(CourseCollection db);

    CourseCollection vo2db(CourseVO courseVO);
}
