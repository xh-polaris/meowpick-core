package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.consts.Consts;
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
import com.xhpolaris.meowpick.infrastructure.pojo.CourseLearnCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseLearnHistoryCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseRepository extends BasicRepository<CourseCollection, CourseVO> implements ICourseRepository {
    private final CourseDao courseDao;
    private final CourseLeanDao leanDao;
    private final CourseLearnHistoryDao historyDao;

    @Override
    public CourseVO createCourse(CourseCmd.CreateCmd cmd) {
        CourseCollection db = CourseMap.instance.cmd2db(cmd);

        courseDao.insert(db);

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
        List<CourseLearnCollection> learn = leanDao.findAllByActiveIsTrueAndCourse(id);

        vo.setData(course);
        vo.setLeaned(learn.stream()
                          .filter(i -> i.getType().equals(Consts.CourseLearn.LEARN))
                          .count());
        vo.setWanted(learn.stream()
                          .filter(i -> i.getType().equals(Consts.CourseLearn.WANT))
                          .count());

        vo.setLearn(Optional.ofNullable(leanDao.findByType(Consts.CourseLearn.LEARN,
                                                           id,
                                                           uid
                                                          ))
                            .map(CourseLearnCollection::isActive)
                            .orElse(false));
        vo.setWant(Optional.ofNullable(leanDao.findByType(Consts.CourseLearn.WANT,
                                                          id,
                                                          uid
                                                         ))
                           .map(CourseLearnCollection::isActive)
                           .orElse(false));

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
    public boolean note(String uid,
                        String course,
                        CourseNoteCmd.CreateCmd cmd,
                        CourseNoteEn en) {
        return false;
    }
}
