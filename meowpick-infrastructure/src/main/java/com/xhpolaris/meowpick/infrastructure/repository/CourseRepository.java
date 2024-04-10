package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.utils.ScoreTransfor;
import com.xhpolaris.meowpick.domain.model.entity.Course;
import com.xhpolaris.meowpick.domain.model.valobj.CourseCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.repository.ICourseRepository;
import com.xhpolaris.meowpick.infrastructure.dao.CourseDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CourseMap;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseRepository implements ICourseRepository {
    private final CourseDao             courseDao;

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

        return map(db);
    }

    private Course map(CourseCollection db) {
        CourseVO course = CourseMap.instance.db2vo(db);

        Course vo = new Course();

        vo.setData(course);


        return vo;
    }

    @Override
    public List<Course> list(List<String> courses) {
        List<CourseCollection> dbList = courseDao.findAllById(courses);
        if (CollectionUtils.isEmpty(dbList)) {
            return List.of();
        }
        return dbList.stream().map(this::map).toList();
    }
}
