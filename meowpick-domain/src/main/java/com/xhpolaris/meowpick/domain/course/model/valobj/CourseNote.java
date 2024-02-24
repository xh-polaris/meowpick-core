package com.xhpolaris.meowpick.domain.course.model.valobj;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CourseNote {

    @Data
    public static class History {
        private String step;
        private String title;
        private String text;
        private List<String> img;
        private Date crateAt;
    }

    private Float score;
    private List<History> histories;
}
