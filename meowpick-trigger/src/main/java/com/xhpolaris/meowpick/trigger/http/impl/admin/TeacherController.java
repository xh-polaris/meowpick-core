package com.xhpolaris.meowpick.trigger.http.impl.admin;

import com.xhpolaris.meowpick.trigger.http.api.TeacherApi;
import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.TeacherCmd;
import com.xhpolaris.meowpick.domain.model.valobj.TeacherVO;
import com.xhpolaris.meowpick.domain.service.TeacherServer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeacherController implements TeacherApi {
    private final TeacherServer service;

    @Override
    public TeacherVO add(TeacherCmd.CreateCmd cmd) {
        return service.add(cmd);
    }

    @Override
    public TeacherVO del(String id) {
        return service.del(id);
    }

    @Override
    public TeacherVO update(TeacherCmd.UpdateCmd cmd) {
        return service.update(cmd);
    }

    @Override
    public PageEntity<TeacherVO> query(TeacherCmd.Query query) {
        return service.query(query);
    }

    @Override
    public TeacherVO get(String id) {
        return service.get(id);
    }

    @Override
    public List<TeacherVO> getBatch(List<String> id) {
        return service.getBatch(id);
    }
}
