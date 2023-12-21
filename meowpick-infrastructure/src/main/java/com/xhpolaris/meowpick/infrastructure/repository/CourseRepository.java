package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.course.model.entity.CourseCmd;
import com.xhpolaris.meowpick.domain.course.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.course.repository.ICourseRepository;
import com.xhpolaris.meowpick.infrastructure.dao.CourseDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CourseMap;
import com.xhpolaris.meowpick.infrastructure.pojo.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseRepository implements ICourseRepository {
    private final CourseDao courseDao;

    @Override
    public CourseVO createCourse(CourseCmd.CreateCmd cmd) {
        Course db = CourseMap.instance.cmd2db(cmd);

        courseDao.insert(db);

        return CourseMap.instance.db2vo(db);
    }

    @Override
    public CourseVO remove(String id) {
        Course db = courseDao.findById(id).orElse(null);
        if (db == null) {
            return null;
        }

        courseDao.deleteById(id);

        return CourseMap.instance.db2vo(db);
    }

    @Override
    public CourseVO updateCourse(CourseCmd.UpdateCmd cmd) {
        Course db = CourseMap.instance.cmd2db(cmd);

        courseDao.save(db);

        return CourseMap.instance.db2vo(db);
    }

    @Override
    public PageEntity<CourseVO> page(CourseCmd.Query query) {
        return null;
    }

    @Override
    public CourseVO getById(String id) {
        Course db = courseDao.findById(id).orElse(null);
        if (db == null) {
            return null;
        }

        return CourseMap.instance.db2vo(db);
    }
}
