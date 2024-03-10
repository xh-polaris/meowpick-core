package com.xhpolaris.meowpick.infrastructure.pojo;

import com.xhpolaris.meowpick.common.enums.CourseNoteEn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("course_learn_history")
public class CourseLearnHistoryCollection {

    @Data
    public static class History {
        private CourseNoteEn enums;
        private String title;
        private String text;
        private List<String> img = new ArrayList<>();
        private Date crateAt;
    }


    private String id;
    private Integer score = 0;
    private String uid;
    private String course;
    private List<History> histories = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

}
