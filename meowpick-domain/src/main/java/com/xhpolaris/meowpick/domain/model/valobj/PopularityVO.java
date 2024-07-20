package com.xhpolaris.meowpick.domain.model.valobj;

import lombok.Data;

import java.util.Date;

@Data
public class PopularityVO {

    // 主键
    private String id;

    // 搜索热度
    private Double popularity;

    // 搜索次数
    private long count;

    // 搜索内容
    private String text;

    // 是否删除
    private Boolean deleted;

    // 创建时间
    private Date createAt;

    // 更新时间
    private Date updateAt;
}
