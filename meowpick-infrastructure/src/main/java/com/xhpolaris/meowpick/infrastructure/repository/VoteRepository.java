package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.model.valobj.VoteStatsCmd;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public VoteStatsVO addCourse(VoteStatsCmd.CreateCmd createCmd) {
        VoteStatsCollection db = VoteMap.instance.cmd2db(createCmd);
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

        if (!voteStats.getIsApproved() && voteStats.getPassVotes() >= PASS_COUNT) {
            // 通过则加入到course数据库
            // TODO 顺便添加老师
            voteStats.setIsApproved(true);
            CourseVO courseDetail = voteStats.getCourseDetail();
            courseDao.save(CourseMap.instance.vo2db(courseDetail));
        }
        // 保存
        voteDao.save(voteStats);
        return VoteMap.instance.db2vo(voteStats);
    }

    @Override
    public VoteStatsVO removeVote(String id) {
        VoteStatsCollection db = voteDao.findById(id).orElse(null);
        if (db == null) {
            return null;
        }
        voteDao.deleteById(id);
        return VoteMap.instance.db2vo(db);
    }


    @Override
    public VoteStatsVO updateVote(VoteStatsCmd.UpdateCmd cmd) {
        VoteStatsCollection db = VoteMap.instance.cmd2db(cmd);
        voteDao.save(db);
        return VoteMap.instance.db2vo(db);
    }

    @Override
    public PageEntity<VoteStatsVO> page(VoteStatsCmd.Query query) {
        Page<VoteStatsCollection> page = voteDao.findAll(
                PageRequest.of(query.getPage(), query.getSize())
        );

        return BasicRepository.page(page, VoteMap.instance::db2vo);
    }
}