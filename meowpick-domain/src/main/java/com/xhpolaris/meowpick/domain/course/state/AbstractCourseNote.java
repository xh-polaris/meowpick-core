package com.xhpolaris.meowpick.domain.course.state;

import com.xhpolaris.meowpick.common.enums.CourseNoteEn;
import com.xhpolaris.meowpick.domain.course.model.entity.CourseNoteCmd;
import com.xhpolaris.meowpick.domain.course.repository.ICourseRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractCourseNote implements ICourseNote{
//    @Resource
    protected final ICourseRepository repository;

    @Override
    public boolean start(String uid,
                         String course,
                         CourseNoteCmd.CreateCmd cmd,
                         CourseNoteEn currentState) {
        throw new UnsupportedOperationException("当前状态无法操作");
    }

    @Override
    public boolean note(String uid,
                        String course,
                        CourseNoteCmd.CreateCmd cmd,
                        CourseNoteEn currentState) {
        throw new UnsupportedOperationException("当前状态无法操作");
    }

    @Override
    public boolean end(String uid,
                       String course,
                       CourseNoteCmd.CreateCmd cmd,
                       CourseNoteEn currentState) {
        throw new UnsupportedOperationException("当前状态无法操作");
    }
}
