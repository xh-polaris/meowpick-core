package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.PopularityCmd;
import com.xhpolaris.meowpick.domain.model.valobj.PopularityVO;
import com.xhpolaris.meowpick.domain.repository.IPopularityRepository;
import com.xhpolaris.meowpick.infrastructure.dao.PopularityDao;
import com.xhpolaris.meowpick.infrastructure.dao.SearchHistoryDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CourseMap;
import com.xhpolaris.meowpick.infrastructure.mapstruct.PopularityMap;
import com.xhpolaris.meowpick.infrastructure.mapstruct.TeacherMap;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.PopularityCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.SearchHistoryCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.TeacherCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class PopularityRepository implements IPopularityRepository {

    // @Autowired
    private final PopularityDao popularityDao;

    // @Autowired
    private final SearchHistoryDao searchHistoryDao;


    // 加入搜索热度（每日新增）
    @Override
    public PopularityVO addPopularity(PopularityCmd.CreateCmd cmd) {
        PopularityCollection db = PopularityMap.instance.cmd2db(cmd);

        popularityDao.save(db);

        return PopularityMap.instance.db2vo(db);
    }

    // 更新搜索热度（每日更新）
    @Override
    public PopularityVO updatePopularity(PopularityCmd.UpdateCmd cmd) {
        PopularityCollection db = PopularityMap.instance.cmd2db(cmd);

        popularityDao.save(db);

        return PopularityMap.instance.db2vo(db);
    }

    // 删除搜索热度（删除 1 个月前的记录）
    @Override
    public void removePopularity() {
        popularityDao.deleteAllByCreateAtBefore(Date.from(LocalDate.now().minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

    }

    // 获取搜索热度（按照热度倒序降序排列）
    @Override
    public PageEntity<PopularityVO> getPopularity(PopularityCmd.Query query) {
        Page<PopularityCollection> page =
                popularityDao.findAllByDeletedIsFalseOrderByPopularityDesc(PageRequest.of(query.getPage(), query.getSize()));

        return BasicRepository.page(page, PopularityMap.instance::db2vo);
    }

    // 每天午夜12点定时更新搜索热度集
    @Scheduled(cron = "0 12 22 * * ? ")
    @Override
    public void updatePopularitySetAtMidnight() {

        // 删除原本数据库中 1 个月之前的数据
        removePopularity();

        // 每日 0 点遍历搜索历史表中近 1 天的搜索记录更新搜索热度集
        searchHistoryDao.findAllByCreateAtGreaterThanAndDeletedIsFalseOrderByCreateAtDesc(
                        Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant()))
                .forEach(searchHistory -> {
                    PopularityCollection popularity = popularityDao.findByText(searchHistory.getText());

                    // 若近 1 天的搜索历史在搜索热度表中不存在，则添加
                    if (popularity == null) {
                        log.info("搜索历史表中不存在新搜索记录：{" + searchHistory.getText() + "}，添加到搜索热度表中");
                        PopularityCollection dp = PopularityMap.instance.cmd2db(PopularityCmd.CreateCmd.builder()
                                .text(searchHistory.getText())
                                .count(searchHistory.getCount())
                                .updateAt(searchHistory.getCreateAt())
                                .deleted(false)
                                .createAt(searchHistory.getCreateAt())
                                .build());
                        dp.setPopularity(calculatePopularity(dp));

                        popularityDao.save(dp);
                    }

                    // 若近 1 天的搜索历史在搜索热度表中存在，则更新
                    if (popularity != null) {
                        log.info("搜索历史表中存在新搜索记录：{" + searchHistory.getText() + "}，更新搜索热度表");
                        PopularityCollection dp = PopularityMap.instance.cmd2db(PopularityCmd.UpdateCmd.builder()
                                .text(searchHistory.getText())
                                .count(popularity.getCount() + searchHistory.getCount())
                                .updateAt(searchHistory.getCreateAt())
                                .deleted(false)
                                .createAt(popularity.getCreateAt())
                                .id(popularity.getId())
                                .build());
                        dp.setPopularity(calculatePopularity(dp));

                        popularityDao.save(dp);
                    }
                });

    }

    // 时间衰减率，可以调整以控制衰减速度
    private static final Double DECAY_RATE = 0.01;

    // 热度计算规则
    private static Double calculatePopularity(PopularityCollection popularity) {
        // 计算时间差
        Instant now = Instant.now();
        Duration duration = Duration.between(popularity.getUpdateAt().toInstant(), now);

        // 计算衰减因子
        double decayFactor = Math.exp(-DECAY_RATE * (duration.toHours() + 2));

        // 计算热度
        return popularity.getCount() * decayFactor;
    }
}
