package com.xhpolaris.meowpick.domain.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.CoursePopularityCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CoursePopularityVO;

public interface ICoursePopularityRepository {

    // 新增课程热度
    CoursePopularityVO addPopularity(CoursePopularityCmd.CreateCmd cmd);

    // 更新课程热度（每日更新）
    CoursePopularityVO updatePopularity(CoursePopularityCmd.UpdateCmd cmd);

    // 获取课程热度总榜（按照热度倒序降序排列）
    PageEntity<CoursePopularityVO> queryPopularity(CoursePopularityCmd.Query query);

    // 获取课程热度日榜（按照热度倒序降序排列）
    PageEntity<CoursePopularityVO> queryPopularityDaily(CoursePopularityCmd.Query query);

    // 每天午夜12点定时更新课程热度排行榜
    void updatePopularitySetAtMidnight();
}
