package com.xhpolaris.meowpick.domain.course.state.impl;

import com.xhpolaris.meowpick.common.enums.CourseNoteEn;
import com.xhpolaris.meowpick.domain.course.model.entity.CourseNoteCmd;
import com.xhpolaris.meowpick.domain.course.state.AbstractCourseNote;
import org.springframework.stereotype.Component;

@Component(CourseNoteEn.Name.note)
public class CourseNote extends AbstractCourseNote {

    @Override
    public boolean note(String uid,
                        String course,
                        CourseNoteCmd.CreateCmd cmd,
                        CourseNoteEn en) {
        return repository.note(uid, course, cmd, en);
    }

    @Override
    public boolean end(String uid,
                       String course,
                       CourseNoteCmd.CreateCmd cmd,
                       CourseNoteEn en) {
        return repository.note(uid, course, cmd, en);
    }
}
