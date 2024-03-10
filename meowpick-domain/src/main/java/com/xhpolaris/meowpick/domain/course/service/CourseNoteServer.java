package com.xhpolaris.meowpick.domain.course.service;

import com.xhpolaris.meowpick.common.enums.CourseNoteEn;
import com.xhpolaris.meowpick.domain.course.model.entity.CourseNoteCmd;
import com.xhpolaris.meowpick.domain.course.model.valobj.CourseNote;
import com.xhpolaris.meowpick.domain.course.repository.ICourseRepository;
import com.xhpolaris.meowpick.domain.course.state.factory.CourseNoteProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseNoteServer {
    private final CourseNoteProvider provider;
    private final ICourseRepository repository;

    public boolean start(String uid,
                         String course,
                         CourseNoteCmd.CreateCmd cmd,
                         CourseNoteEn en) {
        return provider.get(en).start(uid, course, cmd, en);
    }

    public boolean note(String uid,
                        String course,
                        CourseNoteCmd.CreateCmd cmd,
                        CourseNoteEn en) {
        return provider.get(en).note(uid, course, cmd, en);
    }

    public boolean end(String uid,
                       String course,
                       CourseNoteCmd.CreateCmd cmd,
                       CourseNoteEn en) {
        return provider.get(en).end(uid, course, cmd, en);
    }

    public CourseNote list(String uid, String course) {
        return repository.history(uid, course);
    }

    public List<Integer> list(String id) {
        return repository.courseScoreList(id);
    }

    public Map<String, List<Integer>> listIn(List<String> ids) {
        return repository.courseScoreListIn(ids);
    }

    public CourseNoteEn currentState(String uid, String course) {
        return repository.currentState(uid, course);
    }
}
