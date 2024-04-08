package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.enums.CourseNoteEn;
import com.xhpolaris.meowpick.common.exceptions.BizException;
import com.xhpolaris.meowpick.common.utils.TimeUtils;
import com.xhpolaris.meowpick.domain.model.entity.Course;
import com.xhpolaris.meowpick.domain.model.valobj.CourseCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseNoteCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseNote;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.repository.ICourseRepository;
import com.xhpolaris.meowpick.infrastructure.dao.CourseDao;
import com.xhpolaris.meowpick.infrastructure.dao.CourseLearnHistoryDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CourseLearnHistoryMap;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CourseMap;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseLearnHistoryCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseRepository implements ICourseRepository {
    private final CourseDao             courseDao;
    private final CourseLearnHistoryDao historyDao;

    @Override
    public CourseVO createCourse(CourseCmd.CreateCmd cmd) {
        CourseCollection db = CourseMap.instance.cmd2db(cmd);

        courseDao.save(db);

        return CourseMap.instance.db2vo(db);
    }

    @Override
    public CourseVO remove(String id) {
        CourseCollection db = courseDao.findById(id).orElse(null);
        if (db == null) {
            return null;
        }

        courseDao.deleteById(id);

        return CourseMap.instance.db2vo(db);
    }

    @Override
    public CourseVO updateCourse(CourseCmd.UpdateCmd cmd) {
        CourseCollection db = CourseMap.instance.cmd2db(cmd);

        courseDao.save(db);

        return CourseMap.instance.db2vo(db);
    }

    @Override
    public PageEntity<CourseVO> page(CourseCmd.Query query) {
        Page<CourseCollection> page = courseDao.findAll(CourseCollection.toExample(query),
                PageRequest.of(query.getPage(), query.getSize()));
        return BasicRepository.page(page, CourseMap.instance::db2vo);
    }

    @Override
    public Course getById(String id, String uid) {
        CourseCollection db = courseDao.findById(id).orElse(null);
        if (db == null) {
            return null;
        }

        CourseVO course = CourseMap.instance.db2vo(db);

        Course vo = new Course();

        List<CourseLearnHistoryCollection> list = historyDao.findAllByCourse(id);

        List<CourseLearnHistoryCollection.History> historyList =
                list.stream()
                    .map(CourseLearnHistoryCollection::getHistories)
                    .map(e -> e.stream()
                               .findFirst()
                               .orElse(new CourseLearnHistoryCollection.History()))
                    .toList();


        vo.setData(course);
        vo.setWant_cnt(historyList.stream()
                                  .map(CourseLearnHistoryCollection.History::getEnums)
                                  .filter(CourseNoteEn.start::equals)
                                  .count());
        vo.setLearn_cnt(historyList.stream()
                                   .map(CourseLearnHistoryCollection.History::getEnums)
                                   .filter(CourseNoteEn.end::equals)
                                   .count());

        return vo;
    }

    @Override
    public CourseNote history(String uid, String course) {
        CourseLearnHistoryCollection history = historyDao.findByUidAndCourse(uid, course);
        if (history == null) {
            return null;
        }

        CourseNote vo = CourseLearnHistoryMap.instance.db2vo(history);

        vo.setHistories(history.getHistories()
                               .stream()
                               .map(CourseLearnHistoryMap.instance::db2vo)
                               .toList());

        return vo;
    }

    @Override
    public List<Integer> courseScoreList(String id) {
        return historyDao.findAllByCourse(id)
                         .stream()
                         .map(CourseLearnHistoryCollection::getScore)
                         .toList();
    }

    @Override
    public CourseNoteEn currentState(String uid, String course) {
        Integer code = Optional.ofNullable(historyDao.findByUidAndCourse(uid, course))
                               .map(CourseLearnHistoryCollection::getHistories)
                               .orElse(List.of())
                               .stream()
                               .findFirst() // Use findFirst() instead of get(0)
                               .flatMap(history -> Optional.ofNullable(history.getEnums())
                                                           .map(CourseNoteEn::getCode))
                               .orElse(-1);

        try {
            return CourseNoteEn.of(code + 1);
        } catch (BizException ex) {
            return CourseNoteEn.end;
        }
    }

    @Override
    public Map<String, List<Integer>> courseScoreListIn(List<String> ids) {
        List<CourseLearnHistoryCollection> list = historyDao.findAllByCourseIn(ids);
        Map<String, List<CourseLearnHistoryCollection>> groupByCourse = list.stream()
                                                                            .collect(Collectors.groupingBy(CourseLearnHistoryCollection::getCourse));
        return groupByCourse.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                e -> e.getValue().stream().map(CourseLearnHistoryCollection::getScore).toList()));
    }

    @Override
    public List<Course> list(List<String> courses) {
        if (CollectionUtils.isEmpty(courses)) {
            return new ArrayList<>();
        }

        return courses.stream().map(id -> getById(id, null)).toList();
    }

    @Override
    public boolean note(String uid,
                        String course,
                        CourseNoteCmd.CreateCmd cmd,
                        CourseNoteEn en) {
        CourseLearnHistoryCollection history = Optional.ofNullable(historyDao.findByUidAndCourse(uid, course))
                                                       .orElse(new CourseLearnHistoryCollection());
        CourseLearnHistoryCollection.History hs = new CourseLearnHistoryCollection.History();

        hs.setEnums(en);
        hs.setText(cmd.getText());
        hs.setTitle(cmd.getTitle());
        hs.setCrateAt(TimeUtils.nowDate());

        history.setUid(uid);
        history.setCourse(course);
        history.setScore(cmd.getScore());
        history.getHistories().add(0, hs);
        history.setTags(cmd.getTags());

        historyDao.save(history);

        return true;
    }
}
