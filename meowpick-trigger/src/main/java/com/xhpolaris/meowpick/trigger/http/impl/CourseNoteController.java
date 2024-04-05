package com.xhpolaris.meowpick.trigger.http.impl;

import com.xhpolaris.meowpick.trigger.http.api.CourseLearnApi;
import com.xhpolaris.meowpick.domain.Context;
import com.xhpolaris.meowpick.domain.model.valobj.CourseNoteCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseNote;
import com.xhpolaris.meowpick.domain.service.CourseNoteServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CourseNoteController implements CourseLearnApi {
    private final CourseNoteServer service;
    private final Context          context;

//    综合

//    在学 - 分
//    课评 - 分

    @Override
    public boolean start(String id, CourseNoteCmd.CreateCmd cmd) {

        return service.start(context.uid(), id, cmd, service.currentState(context.uid(),
                id));
    }

    @Override
    public boolean note(String id, CourseNoteCmd.CreateCmd cmd) {
        return service.note(context.uid(), id, cmd, service.currentState(context.uid(), id));
    }

    @Override
    public boolean end(String id, CourseNoteCmd.CreateCmd cmd) {
        return service.end(context.uid(), id, cmd, service.currentState(context.uid(), id));
    }

    @Override
    public CourseNote course_note_list(String id) {
        return service.list(context.uid(), id);
    }

    @Override
    public List<Integer> course_score_list(String id) {
        return service.list(id);
    }
}
