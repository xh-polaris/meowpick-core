package com.xhpolaris.meowpick.infrastructure.pojo;

import com.google.type.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("teacher")
public class TeacherCollection {
    private String id;

    private String name;
    private String depart;
    private String position;

    @CreatedDate
    private DateTime crateAt;
    @LastModifiedDate
    private DateTime updateAt;
}
