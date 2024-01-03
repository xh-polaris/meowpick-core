package com.xhpolaris.meowpick.domain.teacher.service;

import com.xhpolaris.meowpick.domain.teacher.repository.ITeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherServer {
    final ITeacherRepository teacherRepository;
}
