package com.xhpolaris.meowpick.infrastructure.mapstruct;

import com.xhpolaris.meowpick.domain.course.model.entity.CourseNoteCmd;
import com.xhpolaris.meowpick.domain.course.model.valobj.CourseNote;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseLearnHistoryCollection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseLearnHistoryMap {
    CourseLearnHistoryMap instance = Mappers.getMapper(CourseLearnHistoryMap.class);

    CourseNote db2vo(CourseLearnHistoryCollection db);
    CourseNote.History db2vo(CourseLearnHistoryCollection.History db);

    CourseLearnHistoryCollection cmd2db(CourseNoteCmd.CreateCmd cmd);
    CourseLearnHistoryCollection cmd2db(CourseNoteCmd.UpdateCmd cmd);
}
