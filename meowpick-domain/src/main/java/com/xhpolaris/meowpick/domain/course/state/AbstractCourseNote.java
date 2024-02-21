package com.xhpolaris.meowpick.domain.course.state;

import com.xhpolaris.meowpick.common.enums.CourseNoteEn;
import com.xhpolaris.meowpick.domain.course.model.entity.CourseNoteCmd;
import com.xhpolaris.meowpick.domain.course.repository.ICourseRepository;

import javax.annotation.Resource;

public abstract class AbstractCourseNote implements ICourseNote{
    @Resource
    protected ICourseRepository repository;

    @Override
    public boolean start(String uid,
                         String course,
                         CourseNoteCmd.CreateCmd cmd,
                         CourseNoteEn currentState) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean note(String uid,
                        String course,
                        CourseNoteCmd.CreateCmd cmd,
                        CourseNoteEn currentState) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean end(String uid,
                       String course,
                       CourseNoteCmd.CreateCmd cmd,
                       CourseNoteEn currentState) {
        throw new UnsupportedOperationException();
    }
}
