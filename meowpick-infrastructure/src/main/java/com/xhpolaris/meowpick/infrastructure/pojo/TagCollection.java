package com.xhpolaris.meowpick.infrastructure.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document("tag_data")
public class TagCollection {
    @MongoId
    private String id;
    private String label;
    @Indexed(unique = true)
    private String key;

    private List<TagCollection> tags;
}
