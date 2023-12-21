package com.xhpolaris.meowpick.infrastructure.mapstruct;

import com.xhpolaris.meowpick.domain.course.model.entity.CourseCmd;
import com.xhpolaris.meowpick.domain.course.model.valobj.CourseVO;
import com.xhpolaris.meowpick.infrastructure.pojo.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMap {
    CourseMap instance = Mappers.getMapper(CourseMap.class);

    Course cmd2db(CourseCmd.CreateCmd cmd);

    Course cmd2db(CourseCmd.UpdateCmd cmd);

    CourseVO db2vo(Course db);
}
