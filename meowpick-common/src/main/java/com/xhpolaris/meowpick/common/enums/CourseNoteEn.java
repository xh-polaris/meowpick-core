package com.xhpolaris.meowpick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CourseNoteEn implements BaseEnum {
    start("想学",Name.start,0),
    note("学习中",Name.note,1),
    end("学过",Name.end,2)
    ;
    private final String msg;
    private final String value;
    private final Integer code;

    public interface Name {
        String start = "course_note_start";
        String note = "course_note_note";
        String end = "course_note_end";
    }

    public static CourseNoteEn of(Integer code) {
        for (CourseNoteEn note : CourseNoteEn.values()) {
            if (note.getCode().equals(code)) {
                return note;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

}
