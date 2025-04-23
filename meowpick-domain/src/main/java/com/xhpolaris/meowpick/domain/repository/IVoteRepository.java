package com.xhpolaris.meowpick.domain.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.VoteStatsCmd;
import com.xhpolaris.meowpick.domain.model.valobj.VoteStatsVO;

import java.util.List;

public interface IVoteRepository {
    /**
     * 新增课程投票
     * @param createCmd 创建课程的基本属性
     * @return 创建完成的对象
     */
    VoteStatsVO addCourse(VoteStatsCmd.CreateCmd createCmd);

    /**
     * 为课程投票
     * @param id voteStats的id
     * @param voteType 投票类型：同意1；反对-1
     */
    VoteStatsVO voteForCourse(String id, Integer voteType);

    // 删除课程投票信息
    VoteStatsVO removeVote(String id);

    // 更新课程投票信息
    VoteStatsVO updateVote(VoteStatsCmd.UpdateCmd cmd);

    // 分页查询
    PageEntity<VoteStatsVO> page(VoteStatsCmd.Query query);

    VoteStatsVO findById(String id);

    List<String> getUidList(String voteId);
}
