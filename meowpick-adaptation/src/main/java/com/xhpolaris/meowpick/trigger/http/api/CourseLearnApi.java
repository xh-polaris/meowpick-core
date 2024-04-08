package com.xhpolaris.meowpick.trigger.http.api;

import com.xhpolaris.meowpick.domain.model.valobj.CourseNoteCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseNote;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "CourseLearnApi", description = "课程学习记录接口")
@RequestMapping("/api/course_learn")
public interface CourseLearnApi {

    @PostMapping("/start/{id}")
    boolean start(@PathVariable String id, @RequestBody CourseNoteCmd.CreateCmd cmd);

    @PostMapping("/note/{id}")
    boolean note(@PathVariable String id, @RequestBody CourseNoteCmd.CreateCmd cmd);

    @PostMapping("/end/{id}")
    boolean end(@PathVariable String id, @RequestBody CourseNoteCmd.CreateCmd cmd);

    @GetMapping("/{id}")
    CourseNote course_note_list(@PathVariable String id);

    @GetMapping("/course/{id}")
    List<Integer> course_score_list(@PathVariable String id);

}
