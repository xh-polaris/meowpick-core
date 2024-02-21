package com.xhpolaris.meowpick.infrastructure.pojo;

import com.xhpolaris.meowpick.common.enums.CourseNoteEn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("course_learn_history")
public class CourseLearnHistoryCollection {

    @Data
    public static class History {
        private String step;
        private String title;
        private String text;
        private List<String> img;
        private Timestamp crateAt;
    }


    private String id;
    private Float score;
    private String uid;
    private String course;
    private List<History> histories;

}
