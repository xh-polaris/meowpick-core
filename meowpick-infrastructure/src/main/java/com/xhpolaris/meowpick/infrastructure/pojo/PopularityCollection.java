package com.xhpolaris.meowpick.infrastructure.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document("popularity")
@AllArgsConstructor
@NoArgsConstructor
public class PopularityCollection {

    // 主键
    @MongoId
    private String id;

    // 搜索热度
    private Double popularity;

    // 搜索次数
    private Long count;

    // 搜索内容
    private String text;

    // 是否删除
    private Boolean deleted;

    // 创建时间
    @CreatedDate
    private Date createAt;

    // 更新时间
    @LastModifiedDate
    private Date updateAt;
}
