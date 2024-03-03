package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.enums.CourseNoteEn;
import com.xhpolaris.meowpick.domain.course.model.aggregate.Course;
import com.xhpolaris.meowpick.domain.course.model.entity.CourseCmd;
import com.xhpolaris.meowpick.domain.course.model.entity.CourseNoteCmd;
import com.xhpolaris.meowpick.domain.course.model.valobj.CourseNote;
import com.xhpolaris.meowpick.domain.course.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.course.repository.ICourseRepository;
import com.xhpolaris.meowpick.infrastructure.dao.CourseDao;
import com.xhpolaris.meowpick.infrastructure.dao.CourseLeanDao;
import com.xhpolaris.meowpick.infrastructure.dao.CourseLearnHistoryDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CourseLearnHistoryMap;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CourseMap;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseLearnHistoryCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseRepository extends BasicRepository<CourseCollection, CourseVO> implements ICourseRepository {
    private final CourseDao             courseDao;
    private final CourseLeanDao         leanDao;
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
        return pageOf(courseDao,
                CourseCollection.toExample(query),
                query,
                CourseMap.instance::db2vo
                     );
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
        CourseLearnHistoryCollection history =
                Optional.ofNullable(historyDao.findByUidAndCourse(uid, id)).orElse(new CourseLearnHistoryCollection());

        List<CourseLearnHistoryCollection.History> historyList = list.stream()
                                                                     .map(CourseLearnHistoryCollection::getHistories)
                                                                     .flatMap(Collection::stream)
                                                                     .toList();

        vo.setScore(history.getScore());
        vo.setData(course);

        vo.setWanted(historyList.stream()
                                .map(CourseLearnHistoryCollection.History::getStep)
                                .filter(i -> CourseNoteEn.start.getValue().equals(i))
                                .count());
        vo.setLeaned(historyList.stream()
                                .map(CourseLearnHistoryCollection.History::getStep)
                                .filter(i -> CourseNoteEn.end.getValue().equals(i))
                                .count());

        vo.setWant(history.getHistories()
                          .stream()
                          .filter(i -> CourseNoteEn.start.getValue().equals(i.getStep()))
                          .toList()
                          .isEmpty());
        vo.setLearn(history.getHistories()
                           .stream()
                           .filter(i -> CourseNoteEn.end.getValue().equals(i.getStep()))
                           .toList()
                           .isEmpty());

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
                               .map(CourseLearnHistoryCollection.History::getEn)
                               .max(Integer::compareTo)
                               .orElse(CourseNoteEn.start.getCode());

        return CourseNoteEn.of(code);
    }

    @Override
    public boolean note(String uid,
                        String course,
                        CourseNoteCmd.CreateCmd cmd,
                        CourseNoteEn en) {
        CourseLearnHistoryCollection history = Optional.ofNullable(historyDao.findByUidAndCourse(uid, course))
                                                       .orElse(new CourseLearnHistoryCollection());
        CourseLearnHistoryCollection.History hs = new CourseLearnHistoryCollection.History();

        hs.setEn(en.getCode());
        hs.setStep(en.getValue());
        hs.setText(cmd.getText());
        hs.setTitle(cmd.getTitle());

        history.setUid(uid);
        history.setCourse(course);
        history.setScore(cmd.getScore());
        history.getHistories().add(hs);
        historyDao.save(history);

        return true;
    }
}
