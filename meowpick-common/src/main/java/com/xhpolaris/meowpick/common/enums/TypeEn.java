package com.xhpolaris.meowpick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeEn implements BaseEnum {
    course("课程", Name.course, 0),
    teacher("教师", Name.teacher, 1),
//    comment("评论", Name.comment, 2),
//    post("帖子", Name.post, 3),
//    group("群组", Name.group, 4),
//    user("用户", Name.user, 5)
    depart("部门", Name.depart, 6),
    category("分类", Name.category, 7),
    ;

    private final String msg;
    private final String value;
    private final Integer code;

    public interface Name {
        String course = "courseEn_course";
        String depart = "courseEn_depart";
        String category = "courseEn_category";
        String teacher = "teacherEn_teacher";
        String comment = "commentEn_component";
        String post = "postEn_component";
        String group = "groupEn_component";
        String user = "userEn_component";
    }
}
