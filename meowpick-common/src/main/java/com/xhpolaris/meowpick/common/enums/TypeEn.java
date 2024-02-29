package com.xhpolaris.meowpick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeEn implements BaseEnum {
    course("课程", Name.course, 0),
    teacher("教师", Name.teacher, 1),
    comment("评论", Name.comment, 2),
    post("帖子", Name.post, 3),
    group("群组", Name.group, 4),
    user("用户", Name.user, 5)
    ;

    private final String msg;
    private final String value;
    private final Integer code;

    public interface Name {
        String course = "courseEn_component";
        String teacher = "teacherEn_component";
        String comment = "commentEn_component";
        String post = "postEn_component";
        String group = "groupEn_component";
        String user = "userEn_component";
    }
}
