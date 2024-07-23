package com.xhpolaris.meowpick.infrastructure.mapstruct;

import com.xhpolaris.meowpick.domain.model.valobj.CoursePopularityCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CoursePopularityVO;
import com.xhpolaris.meowpick.infrastructure.pojo.CoursePopularityCollection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoursePopularityMap {

    CoursePopularityMap instance = Mappers.getMapper(CoursePopularityMap.class);

    CoursePopularityCollection cmd2db(CoursePopularityCmd.CreateCmd cmd);

    CoursePopularityCollection cmd2db(CoursePopularityCmd.UpdateCmd cmd);
}
