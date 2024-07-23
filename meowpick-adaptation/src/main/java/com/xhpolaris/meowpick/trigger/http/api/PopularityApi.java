package com.xhpolaris.meowpick.trigger.http.api;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.CoursePopularityCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CoursePopularityVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "PopularityApi", description = "课程热度排行榜")
@RequestMapping("/api/popularity")
public interface PopularityApi {

    /**
     * 获取课程总热度排行榜（总榜）
     * @return 课程总热度排行榜
     * @apiNote 课程热度排行榜
     * @apiVersion 1.0
     */
    @PostMapping("/total")
    @Operation(summary = "课程总热度排行榜")
    PageEntity<CoursePopularityVO> total(CoursePopularityCmd.Query query);

    /**
     * 获取课程日热度排行榜（日榜）
     * @return 课程日热度排行榜
     * @apiNote 课程热度排行榜
     * @apiVersion 1.0
     */
    @PostMapping("/day")
    @Operation(summary = "课程日热度排行榜")
    PageEntity<CoursePopularityVO> day(CoursePopularityCmd.Query query);
}
