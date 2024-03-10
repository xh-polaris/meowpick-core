package com.xhpolaris.meowpick.domain.course.model.valobj;

import com.xhpolaris.meowpick.common.enums.CourseNoteEn;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CourseNote {

    @Data
    public static class History {
        private CourseNoteEn enums;
        private String       step;
        private String       title;
        private String       text;
        private List<String> img;
        private Date         crateAt;
    }

    private Integer       score;
    private List<History> histories;
}
