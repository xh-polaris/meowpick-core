package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.entity.Course;
import com.xhpolaris.meowpick.domain.model.valobj.CourseCmd;
import com.xhpolaris.meowpick.domain.model.valobj.CourseVO;
import com.xhpolaris.meowpick.domain.model.valobj.SearchCmd;
import com.xhpolaris.meowpick.domain.model.valobj.TeacherVO;
import com.xhpolaris.meowpick.domain.repository.ICourseRepository;
import com.xhpolaris.meowpick.infrastructure.dao.CommentDao;
import com.xhpolaris.meowpick.infrastructure.dao.CourseDao;
import com.xhpolaris.meowpick.infrastructure.dao.TeacherDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.CourseMap;
import com.xhpolaris.meowpick.infrastructure.mapstruct.TeacherMap;
import com.xhpolaris.meowpick.infrastructure.pojo.CommentCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.CourseCollection;
import com.xhpolaris.meowpick.infrastructure.pojo.TeacherCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseRepository implements ICourseRepository {
  private final CourseDao courseDao;
  private final CommentDao commentDao;
  private final TeacherDao teacherDao;

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
    Page<CourseCollection> page =
        courseDao.findAll(
            CourseCollection.toExample(query), PageRequest.of(query.getPage(), query.getSize()));
    PageEntity<CourseVO> data = BasicRepository.page(page, CourseMap.instance::db2vo);
    Map<String, TeacherVO> teacherMap =
        getTeacher(
            data.getRows().stream()
                .map(CourseVO::getTeachers)
                .flatMap(Collection::stream)
                .distinct()
                .toList());
    for (CourseVO vo : data.getRows()) {
      vo.setTeacherList(vo.getTeachers().stream().map(teacherMap::get).toList());
    }
    setTagCount(data.getRows());

    return data;
  }

  private Map<String, TeacherVO> getTeacher(List<String> data) {
    Map<String, TeacherVO> teacherMap =
        teacherDao.findAllByIdIn(data).stream()
            .collect(Collectors.toMap(TeacherCollection::getId, TeacherMap.instance::db2vo));
    return teacherMap;
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

    Map<String, TeacherVO> teacherMap =
        getTeacher(
            Stream.of(course)
                .map(CourseVO::getTeachers)
                .flatMap(Collection::stream)
                .distinct()
                .toList());

    course.setTeacherList(course.getTeachers().stream().map(teacherMap::get).toList());
    vo.setData(course);

    setTagCount(List.of(course));

    return vo;
  }

  private void setTagCount(List<CourseVO> course) {
    Map<String, List<CommentCollection>> commentGroup =
        commentDao.findAllByTargetIn(course.stream().map(CourseVO::getId).toList()).stream()
            .collect(Collectors.groupingBy(CommentCollection::getTarget));
    for (CourseVO vo : course) {
      Map<String, List<String>> targetTag =
          commentGroup.getOrDefault(vo.getId(), new ArrayList<>()).stream()
              .map(CommentCollection::getTags)
              .flatMap(Collection::stream)
              .collect(Collectors.groupingBy(x -> x));
      vo.setTagCount(
          targetTag.keySet().stream()
              .collect(Collectors.toMap(x -> x, x -> targetTag.get(x).size())));
    }
  }

  @Override
  public Collection<String> suggestDepart(String search, Integer pageNum, Integer pageSize) {
    Page<CourseCollection> page =
        courseDao.queryAllByDepartmentLike(search, PageRequest.of(pageNum, pageSize));
    if (!CollectionUtils.isEmpty(page.getContent())) {
      return page.getContent().stream().map(CourseCollection::getDepartment).distinct().toList();
    }
    return List.of();
  }

  @Override
  public PageEntity<?> cagegory(SearchCmd.Query query) {
    Page<CourseCollection> page =
        courseDao.queryAllByCategory(
            query.getKeyword(), PageRequest.of(query.getPage(), query.getSize()));

    if (!CollectionUtils.isEmpty(page.getContent())) {
      PageEntity<CourseVO> data = BasicRepository.page(page, CourseMap.instance::db2vo);

      Map<String, List<CommentCollection>> commentGroup =
          commentDao
              .findAllByTargetIn(data.getRows().stream().map(CourseVO::getId).toList())
              .stream()
              .collect(Collectors.groupingBy(CommentCollection::getTarget));
      Map<String, TeacherVO> teacherMap =
          getTeacher(
              data.getRows().stream()
                  .map(CourseVO::getTeachers)
                  .flatMap(Collection::stream)
                  .distinct()
                  .toList());
      for (CourseVO vo : data.getRows()) {
        Map<String, List<String>> targetTag =
            commentGroup.getOrDefault(vo.getId(), new ArrayList<>()).stream()
                .map(CommentCollection::getTags)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(x -> x));

        vo.setTagCount(
            targetTag.keySet().stream()
                .collect(Collectors.toMap(x -> x, x -> targetTag.get(x).size())));
        vo.setTeacherList(vo.getTeachers().stream().map(teacherMap::get).toList());
      }

      return data;
    }
    return new PageEntity<>();
  }

  @Override
  public PageEntity<?> depart(SearchCmd.Query query) {
    Page<CourseCollection> page =
        courseDao.queryAllByDepartment(
            query.getKeyword(), PageRequest.of(query.getPage(), query.getSize()));

    if (!CollectionUtils.isEmpty(page.getContent())) {
      PageEntity<CourseVO> data = BasicRepository.page(page, CourseMap.instance::db2vo);

      Map<String, List<CommentCollection>> commentGroup =
          commentDao
              .findAllByTargetIn(data.getRows().stream().map(CourseVO::getId).toList())
              .stream()
              .collect(Collectors.groupingBy(CommentCollection::getTarget));
      Map<String, TeacherVO> teacherMap =
          getTeacher(
              data.getRows().stream()
                  .map(CourseVO::getTeachers)
                  .flatMap(Collection::stream)
                  .distinct()
                  .toList());
      for (CourseVO vo : data.getRows()) {
        Map<String, List<String>> targetTag =
            commentGroup.getOrDefault(vo.getId(), new ArrayList<>()).stream()
                .map(CommentCollection::getTags)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(x -> x));

        vo.setTagCount(
            targetTag.keySet().stream()
                .collect(Collectors.toMap(x -> x, x -> targetTag.get(x).size())));
        vo.setTeacherList(vo.getTeachers().stream().map(teacherMap::get).toList());
      }

      return data;
    }
    return new PageEntity<>();
  }

  @Override
  public Map<String, List<CourseVO>> findByTeachers(List<String> list) {
    List<CourseCollection> courses = courseDao.findByTeachersIn(list);
    HashMap<String, List<CourseVO>> data = new HashMap<>();

    Map<String, List<CommentCollection>> commentGroup =
        commentDao
            .findAllByTargetIn(courses.stream().map(CourseCollection::getId).toList())
            .stream()
            .collect(Collectors.groupingBy(CommentCollection::getTarget));
    Map<String, TeacherVO> teacherMap =
        getTeacher(
            courses.stream()
                .map(CourseCollection::getTeachers)
                .flatMap(Collection::stream)
                .distinct()
                .toList());
    for (CourseCollection db : courses) {
      for (String teacher : db.getTeachers()) {
        List<CourseVO> cache = data.getOrDefault(teacher, new ArrayList<>());
        CourseVO course = CourseMap.instance.db2vo(db);
        Map<String, List<String>> targetTag =
            commentGroup.getOrDefault(course.getId(), new ArrayList<>()).stream()
                .map(CommentCollection::getTags)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(x -> x));

        course.setTagCount(
            targetTag.keySet().stream()
                .collect(Collectors.toMap(x -> x, x -> targetTag.get(x).size())));
        course.setTeacherList(course.getTeachers().stream().map(teacherMap::get).toList());

        cache.add(course);
        data.put(teacher, cache);
      }
    }

    return data;
  }

  @Override
  public List<String> categoryList() {
    return courseDao.findAll().stream().map(CourseCollection::getCategory).distinct().toList();
  }

  @Override
  public Collection<String> suggestCategory(String search, Integer pageNum, Integer pageSize) {
    Page<CourseCollection> page =
        courseDao.queryAllByCategoryLike(search, PageRequest.of(pageNum, pageSize));
    if (!CollectionUtils.isEmpty(page.getContent())) {
      return page.getContent().stream().map(CourseCollection::getCategory).distinct().toList();
    }
    return List.of();
  }

  @Override
  public List<CourseVO> suggest(String search, Integer pageNum, Integer pageSize) {
    Page<CourseCollection> page =
        courseDao.queryAllByNameLike(search, PageRequest.of(pageNum, pageSize));

    if (!CollectionUtils.isEmpty(page.getContent())) {
      List<CourseVO> data = page.getContent().stream().map(CourseMap.instance::db2vo).toList();

      Map<String, List<CommentCollection>> commentGroup =
          commentDao.findAllByTargetIn(data.stream().map(CourseVO::getId).toList()).stream()
              .collect(Collectors.groupingBy(CommentCollection::getTarget));
      Map<String, TeacherVO> teacherMap =
          getTeacher(
              data.stream()
                  .map(CourseVO::getTeachers)
                  .flatMap(Collection::stream)
                  .distinct()
                  .toList());
      for (CourseVO vo : data) {
        Map<String, List<String>> targetTag =
            commentGroup.getOrDefault(vo.getId(), new ArrayList<>()).stream()
                .map(CommentCollection::getTags)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(x -> x));

        vo.setTagCount(
            targetTag.keySet().stream()
                .collect(Collectors.toMap(x -> x, x -> targetTag.get(x).size())));
        vo.setTeacherList(vo.getTeachers().stream().map(teacherMap::get).toList());
      }

      return data;
    }
    return List.of();
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
