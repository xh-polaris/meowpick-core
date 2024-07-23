package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.CoursePopularityCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CoursePopularityVO;
import com.xhpolaris.meowpick.domain.repository.ICoursePopularityRepository;
import com.xhpolaris.meowpick.infrastructure.dao.*;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CourseMap;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CoursePopularityMap;
import com.xhpolaris.meowpick.infrastructure.pojo.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static io.prometheus.client.Counter.build;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseCoursePopularityRepository implements ICoursePopularityRepository {

    private final CoursePopularityDao coursePopularityDao;
    private final ClickActionDao clickActionDao;
    private final CommentDao commentDao;
    private final ActionDao actionDao;
    private final CourseDao courseDao;

    // 标记首次更新课程热度排行榜（将已有课程全部初始化加入排行榜中）
    private static Boolean firstUse = true;

    // 新增课程热度
    @Override
    public CoursePopularityVO addPopularity(CoursePopularityCmd.CreateCmd cmd) {
        CoursePopularityCollection db = CoursePopularityMap.instance.cmd2db(cmd);

        coursePopularityDao.save(db);

        return db2vo.apply(db);
    }

    // 更新课程热度（每日更新）
    @Override
    public CoursePopularityVO updatePopularity(CoursePopularityCmd.UpdateCmd cmd) {
        CoursePopularityCollection db = CoursePopularityMap.instance.cmd2db(cmd);

        coursePopularityDao.save(db);

        return db2vo.apply(db);
    }

    // 获取课程热度总榜（按照总热度倒序降序排列）
    @Override
    public PageEntity<CoursePopularityVO> queryPopularity(CoursePopularityCmd.Query query) {
        Page<CoursePopularityCollection> page = coursePopularityDao.findAllByOrderByPopularityDesc(
                PageRequest.of(query.getPage(), query.getSize()));

        PageEntity<CoursePopularityVO> coursePopularityVOPageEntity = new PageEntity<>();
        coursePopularityVOPageEntity.setTotal(page.getTotalElements());
        coursePopularityVOPageEntity.setRows(page.getContent().stream().map(db2vo).toList());

        return coursePopularityVOPageEntity;
    }

    // 获取课程热度日榜（按照日热度倒序降序排列）
    @Override
    public PageEntity<CoursePopularityVO> queryPopularityDaily(CoursePopularityCmd.Query query) {
        Page<CoursePopularityCollection> page = coursePopularityDao.findAllByOrderByDailyPopularityDesc(
                PageRequest.of(query.getPage(), query.getSize()));

        PageEntity<CoursePopularityVO> coursePopularityVOPageEntity = new PageEntity<>();
        coursePopularityVOPageEntity.setTotal(page.getTotalElements());
        coursePopularityVOPageEntity.setRows(page.getContent().stream().map(db2vo).toList());

        return coursePopularityVOPageEntity;
    }

    // 每天午夜12点定时更新课程热度排行榜
    @Scheduled(cron = "0 10 17 * * ? ")
    @Override
    public void updatePopularitySetAtMidnight() {

        final Integer[] createCmdCount = {0};
        final Integer[] updateCmdCount = {0};
        Map<String, Integer> createCmdMap = new HashMap<>();
        Map<String, Integer> updateCmdMap = new HashMap<>();
        List<CoursePopularityCmd.CreateCmd> createCmd = new ArrayList<>();
        List<CoursePopularityCmd.UpdateCmd> updateCmd = new ArrayList<>();

        // 首次使用先将所有的课程都初始化加入课程热度排行榜中
        if (firstUse) {
            courseDao.findAll().forEach(course -> {
                log.info("初始化课程热度排行榜：课程{" + course.getId() + "}，添加到课程热度榜单中");

                // 因为之前学长写的ActionCollect中的字段无法满足现在的每日计算课程对应的点赞数，为了避免改变数据库结构带来的风险，这里不再计算每日的点赞量，以总数作为替代
//                final Long[] dailyLikeCount = {0L};
//
//                actionDao.findAllByTargetIn(commentDao.findAllIdByTarget(course.getId())
//                                .stream()
//                                .map(CommentCollection::getId)
//                                .toList())
//                        .forEach(action -> {
//                            List<ActionCollection.Action> actions = action.getLike()
//                                    .stream()
//                                    .filter(like -> like.getCrateAt().after(Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant())))
//                                    .toList();
//                            dailyLikeCount[0] += actions.size();
//                        });

                CoursePopularityCmd.CreateCmd cmd = CoursePopularityCmd.CreateCmd.builder()
                        .courseId(course.getId())
                        .clickCount(clickActionDao.findByTarget(course.getId()) == null ? 0 : clickActionDao.findByTarget(course.getId()).getClickCount())
                        .dailyClickCount(clickActionDao.findByTarget(course.getId()) == null ? 0 :
                                (long) clickActionDao.findByTarget(course.getId()).getClick()
                                .stream()
                                .map(ClickActionCollection.Action::getCreateAt)
                                .filter(date -> date.after(Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant())))
                                .toList()
                                .size())
                        .commentCount(commentDao.countAllByTarget(course.getId()))
                        .dailyCommentCount(commentDao.countAllByTargetAndUpdateAtAfter(course.getId(), Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant())))
                        .likeCount((long) actionDao.findAllByTargetIn(commentDao.findAllIdByTarget(course.getId())
                                        .stream()
                                        .map(CommentCollection::getId)
                                        .toList())
                                .size())
                        .dailyLikeCount((long) actionDao.findAllByTargetIn(commentDao.findAllIdByTarget(course.getId())
                                        .stream()
                                        .map(CommentCollection::getId)
                                        .toList())
                                .size())
                        .build();

                cmd.setPopularity(calculatePopularity(cmd.getClickCount(), cmd.getCommentCount(), cmd.getLikeCount()));
                cmd.setDailyPopularity(calculatePopularity(cmd.getDailyClickCount(), cmd.getDailyCommentCount(), cmd.getDailyLikeCount()));

                addPopularity(cmd);
            });

            firstUse = false;
        }


        // 每日 0 点根据近 1 天的课程点击量更新课程热度排行榜
        clickActionDao.findAllByUpdateAtAfter(Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant()))
                .forEach(clickAction -> {
                    CoursePopularityCollection popularity = coursePopularityDao.findByCourseId(clickAction.getTarget());

                    // 若近 1 天的课程点击量在课程热度表中不存在，则添加
                    if (popularity == null) {
                        log.info("课程点击量搜索：课程热度榜单中不存在课程{" + clickAction.getTarget() + "}，添加到课程热度榜单中");
                        CoursePopularityCmd.CreateCmd cmd = CoursePopularityCmd.CreateCmd.builder()
                                .courseId(clickAction.getTarget())
                                .clickCount(clickAction.getClickCount())
                                .dailyClickCount((long) clickAction.getClick()
                                        .stream()
                                        .map(ClickActionCollection.Action::getCreateAt)
                                        .filter(date -> date.after(Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant())))
                                        .toList()
                                        .size())
                                .build();

                        if (createCmdMap.containsKey(clickAction.getTarget())) {
                            createCmd.get(createCmdMap.get(clickAction.getTarget())).setClickCount(cmd.getClickCount());
                            createCmd.get(createCmdMap.get(clickAction.getTarget())).setDailyClickCount(cmd.getDailyClickCount());
                        } else {
                            createCmdMap.put(clickAction.getTarget(), createCmdCount[0]);
                            createCmd.add(cmd);
                            createCmdCount[0]++;
                        }
                    }

                    // 若近 1 天的课程点击量在课程热度表中存在，则更新
                    if (popularity != null) {
                        log.info("课程点击量搜索：课程热度榜单中存在课程{" + clickAction.getTarget() + "}，更新课程热度榜单");
                        CoursePopularityCmd.UpdateCmd cmd = CoursePopularityCmd.UpdateCmd.builder()
                                .courseId(popularity.getCourseId())
                                .id(popularity.getId())
                                .clickCount(clickAction.getClickCount())
                                .dailyClickCount((long) clickAction.getClick()
                                        .stream()
                                        .map(ClickActionCollection.Action::getCreateAt)
                                        .filter(date -> date.after(Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant())))
                                        .toList()
                                        .size())
                                .build();

                        if (updateCmdMap.containsKey(clickAction.getTarget())) {
                            updateCmd.get(updateCmdMap.get(clickAction.getTarget())).setClickCount(cmd.getClickCount());
                            updateCmd.get(updateCmdMap.get(clickAction.getTarget())).setDailyClickCount(cmd.getDailyClickCount());
                        } else {
                            updateCmdMap.put(clickAction.getTarget(), updateCmdCount[0]);
                            updateCmd.add(cmd);
                            updateCmdCount[0]++;
                        }
                    }
                });

        // 每日 0 点根据近 1 天的吐槽量更新课程热度排行榜
        commentDao.findAllByUpdateAtAfter(Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant()))
                .forEach(comment -> {
                    CoursePopularityCollection popularity = coursePopularityDao.findByCourseId(comment.getTarget());

                    // 若近 1 天的课程吐槽量在课程热度表中不存在，则添加
                    if (popularity == null) {
                        log.info("课程吐槽量搜索：课程热度榜单中不存在课程{" + comment.getTarget() + "}，添加到课程热度榜单中");
                        CoursePopularityCmd.CreateCmd cmd = CoursePopularityCmd.CreateCmd.builder()
                                .courseId(comment.getTarget())
                                .commentCount(commentDao.countAllByTarget(comment.getTarget()))
                                .dailyCommentCount(commentDao.countAllByTargetAndUpdateAtAfter(comment.getTarget(), Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant())))
                                .build();

                        if (createCmdMap.containsKey(comment.getTarget())) {
                            createCmd.get(createCmdMap.get(comment.getTarget())).setCommentCount(cmd.getCommentCount());
                            createCmd.get(createCmdMap.get(comment.getTarget())).setDailyCommentCount(cmd.getDailyCommentCount());
                        } else {
                            createCmdMap.put(comment.getTarget(), createCmdCount[0]);
                            createCmd.add(cmd);
                            createCmdCount[0]++;
                        }
                    }

                    // 若近 1 天的课程吐槽量在课程热度表中存在，则更新
                    if (popularity != null) {
                        log.info("课程吐槽量搜索：课程热度榜单中存在课程{" + comment.getTarget() + "}，更新课程热度榜单");
                        CoursePopularityCmd.UpdateCmd cmd = CoursePopularityCmd.UpdateCmd.builder()
                                .courseId(popularity.getCourseId())
                                .id(popularity.getId())
                                .commentCount(commentDao.countAllByTarget(comment.getTarget()))
                                .dailyCommentCount(commentDao.countAllByTargetAndUpdateAtAfter(comment.getTarget(), Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant())))
                                .build();

                        if (updateCmdMap.containsKey(comment.getTarget())) {
                            updateCmd.get(updateCmdMap.get(comment.getTarget())).setCommentCount(cmd.getCommentCount());
                            updateCmd.get(updateCmdMap.get(comment.getTarget())).setDailyCommentCount(cmd.getDailyCommentCount());
                        } else {
                            updateCmdMap.put(comment.getTarget(), updateCmdCount[0]);
                            updateCmd.add(cmd);
                            updateCmdCount[0]++;
                        }
                    }
                });



        // 每日 0 点根据近 1 天的吐槽点赞量更新课程热度排行榜
        // 因为之前学长写的ActionCollect中的字段无法满足现在的每日计算课程对应的点赞数，为了避免改变数据库结构带来的风险，这里计算每日课程点赞数是全部课程遍历
        courseDao.findAll()
                .forEach(course -> {
                    CoursePopularityCollection popularity = coursePopularityDao.findByCourseId(course.getId());

//                    final Long[] dailyLikeCount = {0L};
//
//                    actionDao.findAllByTargetIn(commentDao.findAllIdByTarget(course.getId())
//                                    .stream()
//                                    .map(CommentCollection::getId)
//                                    .toList())
//                            .forEach(action -> {
//                                List<ActionCollection.Action> actions = action.getLike()
//                                        .stream()
//                                        .filter(like -> like.getCrateAt().after(Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant())))
//                                        .toList();
//                                dailyLikeCount[0] += actions.size();
//                            });

                    // 若近 1 天的课程吐槽点赞量在课程热度表中不存在，则添加
                    if (popularity == null) {
                        log.info("课程吐槽点赞量搜索：课程热度榜单中不存在课程{" + course.getId() + "}，添加到课程热度榜单中");
                        CoursePopularityCmd.CreateCmd cmd = CoursePopularityCmd.CreateCmd.builder()
                                .courseId(course.getId())
                                .likeCount((long) actionDao.findAllByTargetIn(commentDao.findAllIdByTarget(course.getId())
                                                .stream()
                                                .map(CommentCollection::getId)
                                                .toList())
                                        .size())
                                .dailyLikeCount((long) actionDao.findAllByTargetIn(commentDao.findAllIdByTarget(course.getId())
                                                .stream()
                                                .map(CommentCollection::getId)
                                                .toList())
                                        .size())
                                .build();

                        if (createCmdMap.containsKey(course.getId())) {
                            createCmd.get(createCmdMap.get(course.getId())).setLikeCount(cmd.getLikeCount());
                            createCmd.get(createCmdMap.get(course.getId())).setDailyLikeCount(cmd.getDailyLikeCount());
                        } else {
                            createCmdMap.put(course.getId(), createCmdCount[0]);
                            createCmd.add(cmd);
                            createCmdCount[0]++;
                        }
                    }

                    // 若近 1 天的课程吐槽点赞量在课程热度表中存在，则更新
                    if (popularity != null) {
                        log.info("课程吐槽点赞量搜索：课程热度榜单中存在课程{" + course.getId() + "}，更新课程热度榜单");
                        CoursePopularityCmd.UpdateCmd cmd = CoursePopularityCmd.UpdateCmd.builder()
                                .courseId(popularity.getCourseId())
                                .id(popularity.getId())
                                .likeCount((long) actionDao.findAllByTargetIn(commentDao.findAllIdByTarget(course.getId())
                                                .stream()
                                                .map(CommentCollection::getId)
                                                .toList())
                                        .size())
                                .dailyLikeCount((long) actionDao.findAllByTargetIn(commentDao.findAllIdByTarget(course.getId())
                                                .stream()
                                                .map(CommentCollection::getId)
                                                .toList())
                                        .size())
                                .build();

                        if (updateCmdMap.containsKey(course.getId())) {
                            updateCmd.get(updateCmdMap.get(course.getId())).setLikeCount(cmd.getLikeCount());
                            updateCmd.get(updateCmdMap.get(course.getId())).setDailyLikeCount(cmd.getDailyLikeCount());
                        } else {
                            updateCmdMap.put(course.getId(), updateCmdCount[0]);
                            updateCmd.add(cmd);
                            updateCmdCount[0]++;
                        }
                    }
                });

        // 更新
        for (CoursePopularityCmd.CreateCmd cmd : createCmd) {
            cmd.setPopularity(calculatePopularity(cmd.getClickCount(), cmd.getCommentCount(), cmd.getLikeCount()));
            cmd.setDailyPopularity(calculatePopularity(cmd.getDailyClickCount(), cmd.getDailyCommentCount(), cmd.getDailyLikeCount()));
            addPopularity(cmd);
        }
        for (CoursePopularityCmd.UpdateCmd cmd : updateCmd) {
            cmd.setClickCount(cmd.getClickCount() == null ? coursePopularityDao.findByCourseId(cmd.getCourseId()).getClickCount() : cmd.getClickCount());
            cmd.setDailyClickCount(cmd.getDailyClickCount() == null ? coursePopularityDao.findByCourseId(cmd.getCourseId()).getDailyClickCount() : cmd.getDailyClickCount());
            cmd.setCommentCount(cmd.getCommentCount() == null ? coursePopularityDao.findByCourseId(cmd.getCourseId()).getCommentCount() : cmd.getCommentCount());
            cmd.setDailyCommentCount(cmd.getDailyCommentCount() == null ? coursePopularityDao.findByCourseId(cmd.getCourseId()).getDailyCommentCount() : cmd.getDailyCommentCount());
            cmd.setLikeCount(cmd.getLikeCount() == null ? coursePopularityDao.findByCourseId(cmd.getCourseId()).getLikeCount() : cmd.getLikeCount());
            cmd.setLikeCount(cmd.getDailyLikeCount() == null ? coursePopularityDao.findByCourseId(cmd.getCourseId()).getDailyLikeCount() : cmd.getDailyLikeCount());
            cmd.setPopularity(calculatePopularity(cmd.getClickCount(), cmd.getCommentCount(), cmd.getLikeCount()));
            cmd.setDailyPopularity(calculatePopularity(cmd.getDailyClickCount(), cmd.getDailyCommentCount(), cmd.getDailyLikeCount()));
            updatePopularity(cmd);
        }


    }

    // 时间衰减率，可以调整以控制衰减速度
    private static final Double DECAY_RATE = 0.01;

    // 热度计算规则
    private static Double calculatePopularity(Long clickCount, Long commentCount, Long likeCount) {
//        // 计算时间差
//        Instant now = Instant.now();
//        Duration duration = Duration.between(popularity.getUpdateAt().toInstant(), now);
//
//        // 计算衰减因子
//        double decayFactor = Math.exp(-DECAY_RATE * (duration.toHours() + 2));

        // 课程点击数的权重
        final Double CLICK_WEIGHT = 0.2;
        // 课程吐槽数的权重
        final Double COMMENT_WEIGHT = 0.6;
        // 课程对应吐槽的点赞数的权重
        final Double COMMENT_ACTION_WEIGHT = 0.2;

        // 数据处理
        clickCount = clickCount == null ? 0 : clickCount;
        commentCount = commentCount == null ? 0 : commentCount;
        likeCount = likeCount == null ? 0 : likeCount;

        // 计算热度
        return clickCount * CLICK_WEIGHT + commentCount * COMMENT_WEIGHT + likeCount * COMMENT_ACTION_WEIGHT;
    }

    // 从DB到VO的映射函数
    private final Function<CoursePopularityCollection, CoursePopularityVO> db2vo = db -> {
        CoursePopularityVO vo = new CoursePopularityVO();
        vo.setId(db.getId());
        vo.setCourseId(db.getCourseId());
        vo.setPopularity(db.getPopularity());
        vo.setDailyPopularity(db.getDailyPopularity());
        vo.setClickCount(db.getClickCount());
        vo.setDailyClickCount(db.getDailyClickCount());
        vo.setCommentCount(db.getCommentCount());
        vo.setDailyCommentCount(db.getDailyCommentCount());
        vo.setLikeCount(db.getLikeCount());
        vo.setDailyLikeCount(db.getDailyLikeCount());
        vo.setCreateAt(db.getCreateAt());
        vo.setUpdateAt(db.getUpdateAt());
        vo.setCourse(CourseMap.instance.db2vo(getCourse(db.getCourseId())));
        return vo;
    };

    private CourseCollection getCourse(String courseId) {
        return courseDao.findById(courseId).orElse(null);
    }
}
