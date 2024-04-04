package com.xhpolaris.meowpick.infrastructure.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("teacher")
public class TeacherCollection {
    private String id;

    private String avatar;
    private String name;
    private String uid;

    @CreatedDate
    private Date crateAt;
    @LastModifiedDate
    private Date updateAt;
}
