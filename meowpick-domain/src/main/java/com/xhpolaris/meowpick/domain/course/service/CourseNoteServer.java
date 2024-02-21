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

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseNoteServer {
    private final CourseNoteProvider provider;
    private final ICourseRepository repository;

    public boolean start(String uid,
                         String course,
                         CourseNoteCmd.CreateCmd cmd,
                         CourseNoteEn courseNoteEn) {
        return provider.get(uid, course, courseNoteEn).start(uid, course, cmd, courseNoteEn);
    }

    public boolean note(String uid,
                        String course,
                        CourseNoteCmd.CreateCmd cmd,
                        CourseNoteEn courseNoteEn) {
        return provider.get(uid, course, courseNoteEn).note(uid, course, cmd, courseNoteEn);
    }

    public boolean end(String uid,
                       String course,
                       CourseNoteCmd.CreateCmd cmd,
                       CourseNoteEn courseNoteEn) {
        return provider.get(uid, course, courseNoteEn).end(uid, course, cmd, courseNoteEn);
    }

    public CourseNote list(String uid, String course) {
        return repository.history(uid, course);
    }
}
