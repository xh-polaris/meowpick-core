package com.xhpolaris.meowpick.domain.service;

import com.xhpolaris.meowpick.domain.model.valobj.VoteStatsVO;
import com.xhpolaris.meowpick.domain.model.valobj.VoteVO;
import com.xhpolaris.meowpick.domain.repository.ICourseRepository;
import com.xhpolaris.meowpick.domain.repository.ITeacherRepository;
import com.xhpolaris.meowpick.domain.repository.IVoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteServer {
    private final IVoteRepository voteRepository;
    private final ICourseRepository courseRepository;
    private final ITeacherRepository teacherRepository;

    public VoteStatsVO addCourse(VoteStatsVO voteStatsVO) {
        return voteRepository.addCourse(voteStatsVO);
    }

    public VoteStatsVO voteForCourse(VoteVO voteVO) {
        return voteRepository.voteForCourse(voteVO.getCourseId(), voteVO.getVoteType());
    }
}
