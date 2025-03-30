package com.xhpolaris.meowpick.domain.repository;

import com.xhpolaris.meowpick.domain.model.valobj.VoteStatsVO;

public interface IVoteRepository {
    /**
     * 新增课程投票
     * @param voteStatsVO 创建课程的基本属性
     * @return 创建完成的对象
     */
    VoteStatsVO addCourse(VoteStatsVO voteStatsVO);

    /**
     * 为课程投票
     * @param courseId 课程id
     * @param voteType 投票类型：同意1；反对-1
     */
    VoteStatsVO voteForCourse(String courseId, Integer voteType);
}
