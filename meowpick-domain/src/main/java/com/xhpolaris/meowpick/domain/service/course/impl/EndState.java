package com.xhpolaris.meowpick.domain.service.course.impl;

import com.xhpolaris.meowpick.common.enums.CourseNoteEn;
import com.xhpolaris.meowpick.domain.repository.ICourseRepository;
import com.xhpolaris.meowpick.domain.service.course.AbstractCourseNote;
import org.springframework.stereotype.Component;

@Component(CourseNoteEn.Name.end)
public class EndState extends AbstractCourseNote {
    public EndState(ICourseRepository repository) {
        super(repository);
    }
}
