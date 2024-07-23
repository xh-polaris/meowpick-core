package com.xhpolaris.meowpick.domain.service;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.CoursePopularityCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CoursePopularityVO;
import com.xhpolaris.meowpick.domain.repository.ICoursePopularityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CoursePopularityServer {

    private final ICoursePopularityRepository repository;

    // 获取课程总热度排行榜（总榜）
    public PageEntity<CoursePopularityVO> total(CoursePopularityCmd.Query query) {
        return repository.queryPopularity(query);
    }

    // 获取课程日热度排行榜（日榜）
    public PageEntity<CoursePopularityVO> day(CoursePopularityCmd.Query query) {
        return repository.queryPopularityDaily(query);
    }
}
