package com.xhpolaris.meowpick.domain.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.TeacherCmd;
import com.xhpolaris.meowpick.domain.model.valobj.TeacherVO;

import java.util.List;

public interface ITeacherRepository {
    TeacherVO add(TeacherCmd.CreateCmd cmd);

    TeacherVO remove(String id);

    TeacherVO update(TeacherCmd.UpdateCmd cmd);

    PageEntity<TeacherVO> query(TeacherCmd.Query query);

    TeacherVO get(String id);

    List<TeacherVO> in(List<String> id);

    List<TeacherVO> suggect(String search, Integer pageNum, Integer pageSize);

    PageEntity<TeacherVO> search(String keyword, Integer page, Integer size);
}
