package com.xhpolaris.meowpick.infrastructure.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document("course_learn")
@AllArgsConstructor
@NoArgsConstructor
public class CourseLearnCollection {
    @MongoId
    private String id;
    private String uid;
    private String course;
    private boolean active;
    private String type;
}
