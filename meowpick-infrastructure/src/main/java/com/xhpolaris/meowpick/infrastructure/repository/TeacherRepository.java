package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.domain.teacher.repository.ITeacherRepository;
import com.xhpolaris.meowpick.infrastructure.dao.TeacherDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherRepository implements ITeacherRepository {
    private final TeacherDao teacherDao;
}
