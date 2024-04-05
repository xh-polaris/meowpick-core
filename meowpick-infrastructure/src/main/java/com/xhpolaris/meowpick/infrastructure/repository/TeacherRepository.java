package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.exceptions.BizException;
import com.xhpolaris.meowpick.domain.model.valobj.TeacherCmd;
import com.xhpolaris.meowpick.domain.model.valobj.TeacherVO;
import com.xhpolaris.meowpick.domain.repository.ITeacherRepository;
import com.xhpolaris.meowpick.infrastructure.dao.TeacherDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.TeacherMap;
import com.xhpolaris.meowpick.infrastructure.pojo.TeacherCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TeacherRepository implements ITeacherRepository {
    private final TeacherDao teacherDao;

    @Override
    public TeacherVO add(TeacherCmd.CreateCmd cmd) {
        TeacherCollection db = TeacherMap.instance.cmd2db(cmd);
        teacherDao.save(db);
        return TeacherMap.instance.db2vo(db);
    }

    @Override
    public TeacherVO remove(String id) {
        TeacherVO vo = get(id);
        teacherDao.deleteById(id);
        return vo;
    }

    @Override
    public TeacherVO update(TeacherCmd.UpdateCmd cmd) {
        TeacherCollection db = TeacherMap.instance.cmd2db(cmd);
        teacherDao.save(db);
        return TeacherMap.instance.db2vo(db);
    }

    @Override
    public PageEntity<TeacherVO> query(TeacherCmd.Query query) {
        Page<TeacherCollection> page = teacherDao.findAll(PageRequest.of(query.getPage(), query.getSize()));

        return BasicRepository.page(page, TeacherMap.instance::db2vo);
    }

    @Override
    public TeacherVO get(String id) {
        TeacherCollection db = teacherDao.findById(id)
                                         .orElseThrow(BizException::NotFind);
        return TeacherMap.instance.db2vo(db);
    }

    @Override
    public List<TeacherVO> in(List<String> id) {
        return teacherDao.findAllByIdIn(id)
                         .stream()
                         .map(TeacherMap.instance::db2vo)
                         .toList();
    }
}
