package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.model.valobj.TeacherVO;
import com.xhpolaris.meowpick.domain.model.valobj.VoteStatsCmd;
import com.xhpolaris.meowpick.domain.model.valobj.VoteStatsVO;
import com.xhpolaris.meowpick.domain.repository.IVoteRepository;
import com.xhpolaris.meowpick.infrastructure.dao.CourseDao;
import com.xhpolaris.meowpick.infrastructure.dao.TeacherDao;
import com.xhpolaris.meowpick.infrastructure.dao.VoteDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CourseMap;
import com.xhpolaris.meowpick.infrastructure.mapstruct.TeacherMap;
import com.xhpolaris.meowpick.infrastructure.mapstruct.VoteMap;
import com.xhpolaris.meowpick.infrastructure.pojo.TeacherCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.VoteStatsCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoteRepository implements IVoteRepository {
    private final VoteDao voteDao;
    private final CourseDao courseDao;
    private final TeacherDao teacherDao;

    private static final Integer PASS_COUNT = 5;

    @Override
    public VoteStatsVO addCourse(VoteStatsCmd.CreateCmd createCmd) {
        VoteStatsCollection db = VoteMap.instance.cmd2db(createCmd);
        db.setPassVotes(0);
        db.setRejectVotes(0);
        db.setTotalVotes(0);
        db.setCourseId(String.valueOf(UUID.randomUUID()));
        db.setIsApproved(false);
        voteDao.save(db);
        return VoteMap.instance.db2vo(db);
    }

    @Override
    public VoteStatsVO voteForCourse(String id, Integer voteType) {
        VoteStatsCollection voteStats = voteDao.findById(id).orElse(null);
        // 检查是否存在、过期
        if (voteStats == null) {
            return null;
        } else if (isExpired(voteStats.getEndTime())) {
            // 过期，删除并返回null
            voteDao.deleteById(voteStats.getId());
            return null;
        }

        // 更新投票数
        voteStats.setTotalVotes(voteStats.getTotalVotes() + 1);
        // 根据投票类型更新
        if (voteType == 1) {
            voteStats.setPassVotes(voteStats.getPassVotes() + 1);
        } else if (voteType == -1) {
            voteStats.setRejectVotes(voteStats.getRejectVotes() + 1);
        }

        // 投票通过，加入course数据库并删除vote数据库中的数据
        if (!voteStats.getIsApproved() && voteStats.getPassVotes() >= PASS_COUNT) {
            // 通过则加入到course数据库
            CourseVO courseDetail = voteStats.getCourseDetail();
            // 先存老师，再存课程
            List<String> teachers = new ArrayList<>();
            List<TeacherVO> tempTeachersList = courseDetail.getTeacherList();
            for (TeacherVO teacherVO : tempTeachersList) {
                // 根据老师名称、部门、职称唯一确定一位老师
                TeacherCollection teacherCollection = teacherDao.findByNameAndDepartAndPosition(teacherVO.getName(), teacherVO.getDepart(), teacherVO.getPosition());
                if (teacherCollection == null) {
                    // 教师不存在，新增
                    TeacherCollection save = teacherDao.save(TeacherMap.instance.vo2db(teacherVO));
                    teachers.add(save.getName());
                } else {
                    teachers.add(teacherCollection.getName());
                }
            }
            courseDetail.setTeachers(teachers);
            courseDao.save(CourseMap.instance.vo2db(courseDetail));

            // 删除vote中的数据
            voteStats.setIsApproved(true);
            voteDao.deleteById(voteStats.getId());
        } else {
            // 保存
            voteDao.save(voteStats);
        }
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
        VoteStatsCollection db = voteDao.findById(cmd.getId()).orElse(null);
        if (db == null) {
            return null;
        }
        voteDao.save(VoteMap.instance.cmd2db(cmd));

        return VoteMap.instance.db2vo(db);
    }

    @Override
    public PageEntity<VoteStatsVO> page(VoteStatsCmd.Query query) {
        // 查询时删除过期的数据
        for (VoteStatsCollection collection : voteDao.findAll()) {
            if (isExpired(collection.getEndTime())) {
                voteDao.deleteById(collection.getId());
            }
        }

        Page<VoteStatsCollection> page = voteDao.findAll(
                PageRequest.of(query.getPage(), query.getSize())
        );

        return BasicRepository.page(page, VoteMap.instance::db2vo);
    }

    @Override
    public VoteStatsVO findById(String id) {
        // 过期删除
        VoteStatsCollection db = voteDao.findById(id).orElse(null);
        if (db == null) {
            return null;
        } else if (isExpired(db.getEndTime())) {
            voteDao.deleteById(db.getId());
            return null;
        }
        return VoteMap.instance.db2vo(db);
    }

    // 判断是否过期
    private boolean isExpired(long endTime) {
        return endTime < System.currentTimeMillis();
    }
}