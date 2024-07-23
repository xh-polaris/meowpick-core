package com.xhpolaris.meowpick.trigger.http.impl;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.CoursePopularityCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CoursePopularityVO;
import com.xhpolaris.meowpick.domain.service.CoursePopularityServer;
import com.xhpolaris.meowpick.infrastructure.pojo.CoursePopularityCollection;
import com.xhpolaris.meowpick.trigger.http.api.PopularityApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PopularityController implements PopularityApi {

    private final CoursePopularityServer coursePopularityServer;

    // 获取课程总热度排行榜（总榜）
    @Override
    public PageEntity<CoursePopularityVO> total(CoursePopularityCmd.Query query) {
        return coursePopularityServer.total(query);
    }

    // 获取课程日热度排行榜（日榜）
    @Override
    public PageEntity<CoursePopularityVO> day(CoursePopularityCmd.Query query) {
        return coursePopularityServer.day(query);
    }
}
