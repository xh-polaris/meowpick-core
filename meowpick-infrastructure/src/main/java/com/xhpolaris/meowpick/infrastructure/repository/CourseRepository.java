package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.consts.Consts;
import com.xhpolaris.meowpick.domain.course.model.aggregate.Course;
import com.xhpolaris.meowpick.domain.course.model.entity.CourseCmd;
import com.xhpolaris.meowpick.domain.course.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.course.repository.ICourseRepository;
import com.xhpolaris.meowpick.infrastructure.dao.CourseDao;
import com.xhpolaris.meowpick.infrastructure.dao.CourseLeanDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CourseMap;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseLearnCollection;
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
    private final MongoTemplate template;
    private final CourseLeanDao leanDao;

    @Override
    public CourseVO createCourse(CourseCmd.CreateCmd cmd) {
        CourseCollection db = CourseMap.instance.cmd2db(cmd);

        courseDao.insert(db);

        return CourseMap.instance.db2vo(db);
    }

    @Override
    public CourseVO remove(String id) {
        CourseCollection db = courseDao.findById(id)
                                       .orElse(null);
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
        return pageOf(
                courseDao,
                CourseCollection.toExample(query),
                query,
                CourseMap.instance::db2vo
                     );
    }

    @Override
    public Course getById(String id, String uid) {
        CourseCollection db = courseDao.findById(id)
                                       .orElse(null);
        if (db == null) {
            return null;
        }

        CourseVO course = CourseMap.instance.db2vo(db);

        Course vo = new Course();
        List<CourseLearnCollection> learn = leanDao.findAllByActiveIsTrueAndCourse(id);

        vo.setData(course);
        vo.setLeaned(learn.stream().filter(i -> i.getType().equals(Consts.CourseLearn.LEARN)).count());
        vo.setWanted(learn.stream().filter(i -> i.getType().equals(Consts.CourseLearn.WANT)).count());

        vo.setLearn(Optional.ofNullable(leanDao.findByType(Consts.CourseLearn.LEARN, id, uid))
                             .map(CourseLearnCollection::isActive).orElse(false));
        vo.setWant(Optional.ofNullable(leanDao.findByType(Consts.CourseLearn.WANT, id, uid))
                            .map(CourseLearnCollection::isActive).orElse(false));

        return vo;
    }

    @Override
    public boolean learned(String id, String uid) {
        CourseLearnCollection db = leanDao.findByType(Consts.CourseLearn.LEARN, id, uid);
        if (db == null) {
            db = new CourseLearnCollection();
            db.setUid(uid);
            db.setType(Consts.CourseLearn.LEARN);
            db.setCourse(id);
            db.setActive(false);
        }

        db.setActive(! db.isActive());

        leanDao.save(db);

        return true;
    }

    @Override
    public boolean want2learn(String id, String uid) {
        return false;
    }
}
