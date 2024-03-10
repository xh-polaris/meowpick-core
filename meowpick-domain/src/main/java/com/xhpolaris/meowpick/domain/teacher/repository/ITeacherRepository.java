package com.xhpolaris.meowpick.domain.teacher.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.teacher.model.entity.TeacherCmd;
import com.xhpolaris.meowpick.domain.teacher.model.valobj.TeacherVO;

import java.util.List;

public interface ITeacherRepository {
    TeacherVO add(TeacherCmd.CreateCmd cmd);

    TeacherVO remove(String id);

    TeacherVO update(TeacherCmd.UpdateCmd cmd);

    PageEntity<TeacherVO> query(TeacherCmd.Query query);

    TeacherVO get(String id);

    List<TeacherVO> in(List<String> id);
}
