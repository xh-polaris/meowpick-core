package com.xhpolaris.meowpick.domain.course.state.factory;

import com.xhpolaris.meowpick.common.enums.CourseNoteEn;
import com.xhpolaris.meowpick.domain.course.state.ICourseNote;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CourseNoteProvider {
    private final Map<String, ICourseNote> noteMap;

    public ICourseNote get(String uid, String course, CourseNoteEn en) {
        return noteMap.get(en.getValue());
    }
}
