package com.xhpolaris.meowpick.domain.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.PopularityCmd;
import com.xhpolaris.meowpick.domain.model.valobj.PopularityVO;

import java.util.List;

public interface IPopularityRepository {

    // 加入搜索热度（每日新增）
    PopularityVO addPopularity(PopularityCmd.CreateCmd cmd);

    // 更新搜索热度（每日新增）
    PopularityVO updatePopularity(PopularityCmd.UpdateCmd cmd);

    // 删除搜索热度（淘汰 1 个月之前的记录）
    void removePopularity();

    // 获取搜索热度（按照热度倒序降序排列）
    PageEntity<PopularityVO> getPopularity(PopularityCmd.Query query);

    // 每天午夜12点定时更新搜索热度集
    void updatePopularitySetAtMidnight();
}
