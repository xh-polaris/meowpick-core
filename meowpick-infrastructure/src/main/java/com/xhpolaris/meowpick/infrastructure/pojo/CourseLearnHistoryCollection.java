package com.xhpolaris.meowpick.infrastructure.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
        private Integer en;
        private String step;
        private String title;
        private String text;
        private List<String> img = new ArrayList<>();
        @CreatedDate
        private Date crateAt;
        @LastModifiedDate
        private Date updateAt;
    }


    private String id;
    private Integer score = 0;
    private String uid;
    private String course;
    private List<History> histories = new ArrayList<>();

}
