package com.xhpolaris.meowpick.domain.service.course.impl;

import com.xhpolaris.meowpick.common.enums.CourseNoteEn;
import com.xhpolaris.meowpick.domain.model.valobj.CourseNoteCmd;
import com.xhpolaris.meowpick.domain.repository.ICourseRepository;
import com.xhpolaris.meowpick.domain.service.course.AbstractCourseNote;
import org.springframework.stereotype.Component;

@Component(CourseNoteEn.Name.note)
public class NoteState extends AbstractCourseNote {

    public NoteState(ICourseRepository repository) {
        super(repository);
    }

    @Override
    public boolean note(String uid,
                        String course,
                        CourseNoteCmd.CreateCmd cmd,
                        CourseNoteEn en) {
        return repository.note(uid, course, cmd, CourseNoteEn.note);
    }

    @Override
    public boolean end(String uid,
                       String course,
                       CourseNoteCmd.CreateCmd cmd,
                       CourseNoteEn en) {
        return repository.note(uid, course, cmd, CourseNoteEn.end);
    }
}
