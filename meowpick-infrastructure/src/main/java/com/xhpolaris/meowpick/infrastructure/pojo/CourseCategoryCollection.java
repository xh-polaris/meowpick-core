package com.xhpolaris.meowpick.infrastructure.pojo;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("course_category")
public class CourseCategoryCollection {
    @MongoId
    private String id;

    private String label;

}
