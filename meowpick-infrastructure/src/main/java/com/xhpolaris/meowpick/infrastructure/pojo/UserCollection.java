package com.xhpolaris.meowpick.infrastructure.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document("user")
@NoArgsConstructor
@AllArgsConstructor
public class UserCollection {
    @MongoId
    private String id;
    private String name;
    private String avatar;


}
