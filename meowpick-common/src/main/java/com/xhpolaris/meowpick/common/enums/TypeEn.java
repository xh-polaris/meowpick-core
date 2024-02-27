package com.xhpolaris.meowpick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeEn implements BaseEnum {
    course("", Name.course, 0),
    teacher("", Name.teacher, 1),
    comment("", Name.comment, 2),
    post("", Name.post, 3);

    private final String msg;
    private final String value;
    private final Integer code;

    public interface Name {
        String course = "course";
        String teacher = "teacher";
        String comment = "comment";
        String post = "post";
    }
}
