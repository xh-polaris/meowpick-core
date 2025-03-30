package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.model.valobj.VoteStatsVO;
import com.xhpolaris.meowpick.domain.repository.IVoteRepository;
import com.xhpolaris.meowpick.infrastructure.dao.CourseDao;
import com.xhpolaris.meowpick.infrastructure.dao.TeacherDao;
import com.xhpolaris.meowpick.infrastructure.dao.VoteDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CourseMap;
import com.xhpolaris.meowpick.infrastructure.mapstruct.VoteMap;
import com.xhpolaris.meowpick.infrastructure.pojo.VoteStatsCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoteRepository implements IVoteRepository {
    private final VoteDao voteDao;
    private final CourseDao courseDao;
    private final TeacherDao teacherDao;

    private final Integer PASS_COUNT = 5;

    @Override
    public VoteStatsVO addCourse(VoteStatsVO voteStatsVO) {
        VoteStatsCollection db = VoteMap.instance.vo2db(voteStatsVO);
        voteDao.save(db);
        return VoteMap.instance.db2vo(db);
    }

    @Override
    public VoteStatsVO voteForCourse(String courseId, Integer voteType) {
        VoteStatsCollection voteStats = voteDao.findByCourseId(courseId);
        // 更新投票数
        voteStats.setTotalVotes(voteStats.getTotalVotes() + 1);
        // 根据投票类型更新
        if (voteType == 1) {
            voteStats.setPassVotes(voteStats.getPassVotes() + 1);
        } else if (voteType == -1) {
            voteStats.setRejectVotes(voteStats.getRejectVotes() + 1);
        }
        // TODO 判断是否通过
        if (!voteStats.getIsApproved() && voteStats.getPassVotes() >= PASS_COUNT) {
            // 通过则加入到course数据库
            // TODO 删除vote数据库中的数据?
            // TODO 添加老师？
            voteStats.setIsApproved(true);
            CourseVO courseDetail = voteStats.getCourseDetail();
            courseDao.save(CourseMap.instance.vo2db(courseDetail));
        }
        // 保存
        voteDao.save(voteStats);
        return VoteMap.instance.db2vo(voteStats);
    }
}
