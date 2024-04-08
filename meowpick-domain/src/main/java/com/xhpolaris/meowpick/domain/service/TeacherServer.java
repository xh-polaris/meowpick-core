package com.xhpolaris.meowpick.domain.service;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.TeacherCmd;
import com.xhpolaris.meowpick.domain.repository.ITeacherRepository;
import com.xhpolaris.meowpick.domain.model.valobj.TeacherVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServer {
    final ITeacherRepository teacherRepository;

    public TeacherVO add(TeacherCmd.CreateCmd cmd) {
        return teacherRepository.add(cmd);
    }

    public TeacherVO del(String id) {
        return teacherRepository.remove(id);
    }

    public TeacherVO update(TeacherCmd.UpdateCmd cmd) {
        return teacherRepository.update(cmd);
    }

    public PageEntity<TeacherVO> query(TeacherCmd.Query query) {
        return teacherRepository.query(query);
    }

    public TeacherVO get(String id) {
        return teacherRepository.get(id);
    }

    public List<TeacherVO> getBatch(List<String> id) {
        if (CollectionUtils.isEmpty(id)) {
            return Collections.emptyList();
        }
        return teacherRepository.in(id);
    }
}
