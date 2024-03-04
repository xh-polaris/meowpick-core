package com.xhpolaris.meowpick.infrastructure.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
@Document("user_third_account")
@NoArgsConstructor
@AllArgsConstructor
public class UserThirdCollection {
    @MongoId
    private String id;

    private String userId;

    private String type;
    private String token;
    private boolean bind = true;

    @CreatedDate
    private Date crateAt;
    @LastModifiedDate
    private Date updateAt;
}
