package com.xhpolaris.meowpick.domain.service.course.factory;

import com.xhpolaris.meowpick.common.enums.CourseNoteEn;
import com.xhpolaris.meowpick.domain.service.course.ICourseNote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CourseNoteProvider {
    private final Map<String, ICourseNote> noteMap;

    public ICourseNote get(CourseNoteEn en) {
        return noteMap.get(en.getValue());
    }
}
