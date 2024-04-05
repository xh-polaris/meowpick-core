package com.xhpolaris.meowpick.domain.service.course;

import com.xhpolaris.meowpick.common.enums.CourseNoteEn;
import com.xhpolaris.meowpick.domain.model.valobj.CourseNoteCmd;

public interface ICourseNote {

    boolean start(String uid, String course, CourseNoteCmd.CreateCmd cmd, CourseNoteEn currentState);
    boolean note(String uid, String course, CourseNoteCmd.CreateCmd cmd, CourseNoteEn currentState);
    boolean end(String uid, String course, CourseNoteCmd.CreateCmd cmd, CourseNoteEn currentState);
}
