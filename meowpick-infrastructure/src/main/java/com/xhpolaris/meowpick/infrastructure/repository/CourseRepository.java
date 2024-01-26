package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.course.model.entity.CourseCmd;
import com.xhpolaris.meowpick.domain.course.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.course.repository.ICourseRepository;
import com.xhpolaris.meowpick.infrastructure.dao.CourseDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CourseMap;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseRepository extends BasicRepository<CourseCollection, CourseVO> implements ICourseRepository {
    private final CourseDao courseDao;
    private final MongoTemplate template;

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
                      CourseMap.instance::db2vo);
    }

    @Override
    public CourseVO getById(String id) {
        CourseCollection db = courseDao.findById(id).orElse(null);
        if (db == null) {
            return null;
        }

        return CourseMap.instance.db2vo(db);
    }

    @Override
    public boolean learned(String id, String uid) {
        return false;
    }

    @Override
    public boolean want2learn(String id, String uid) {
        return false;
    }
}
