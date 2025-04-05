package com.xhpolaris.meowpick.infrastructure.mapstruct;

import com.xhpolaris.meowpick.domain.model.valobj.TeacherCmd;
import com.xhpolaris.meowpick.domain.model.valobj.TeacherVO;
import com.xhpolaris.meowpick.infrastructure.pojo.TeacherCollection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherMap {
    TeacherMap instance = Mappers.getMapper(TeacherMap.class);

    TeacherVO db2vo(TeacherCollection db);

    TeacherCollection cmd2db(TeacherCmd.CreateCmd cmd);
    TeacherCollection cmd2db(TeacherCmd.UpdateCmd cmd);

    TeacherCollection vo2db(TeacherVO vo);
}
