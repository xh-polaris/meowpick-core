package com.xhpolaris.meowpick.domain.service;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.common.event.SearchEvent;
import com.xhpolaris.meowpick.domain.model.valobj.PopularityCmd;
import com.xhpolaris.meowpick.domain.model.valobj.SearchCmd;
import com.xhpolaris.meowpick.domain.model.valobj.SearchHistoryVO;
import com.xhpolaris.meowpick.domain.repository.ISearcherRepository;
import com.xhpolaris.meowpick.domain.service.search.ISearcher;
import com.xhpolaris.meowpick.domain.service.search.factory.SearchProvider;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class SearchServer {
  private final SearchProvider provider;
  private final Context context;
  private final ISearcherRepository repository;

  private final TeacherServer teacherServer;
  private final CourseServer courseServer;

  public PageEntity<String> guess(PopularityCmd.Query query) {
    return repository.guess(query);
  }

  public PageEntity<?> query(SearchCmd.Query query) {
    context.publish(new SearchEvent(context.uid(), query.getType().getValue(), query.getKeyword()));

    ISearcher searcher = provider.query(query);
    return searcher.search(query);
  }

  public List<SearchHistoryVO> recent() {
    return repository.recent(context.uid());
  }

  public void note(SearchEvent event) {
    repository.note(event);
  }

  public Integer total() {
    return repository.total();
  }

  public boolean recentRemove(String id) {
    return repository.recentRemove(id);
  }

  @Data
  static class Suggest {
    private String type;
    private Object data;
  }

  public List<Suggest> suggest(String search, Integer pageNum, Integer pageSize) {
    ConcurrentHashMap<String, List<Suggest>> data = new ConcurrentHashMap<>();

    CompletableFuture<Void> teacherF =
        CompletableFuture.runAsync(
            () ->
                data.put(
                    "teacherServer",
                    teacherServer.suggest(search, pageNum, pageSize).stream()
                        .map(
                            i -> {
                              Suggest s = new Suggest();
                              s.data = i;
                              s.type = "teacher";
                              return s;
                            })
                        .toList()));
    CompletableFuture<Void> courseF =
        CompletableFuture.runAsync(
            () ->
                data.put(
                    "courseServer",
                    courseServer.suggestName(search, pageNum, pageSize).stream()
                        .map(
                            i -> {
                              Suggest s = new Suggest();
                              s.data = i;
                              s.type = "course";
                              return s;
                            })
                        .toList()));
    CompletableFuture<Void> depart =
        CompletableFuture.runAsync(
            () ->
                data.put(
                    "depart",
                    courseServer.suggestDepart(search, pageNum, pageSize).stream()
                        .map(
                            i -> {
                              Suggest s = new Suggest();
                              s.data = i;
                              s.type = "depart";
                              return s;
                            })
                        .toList()));
    CompletableFuture<Void> category =
        CompletableFuture.runAsync(
            () ->
                data.put(
                    "category",
                    courseServer.suggestCategory(search, pageNum, pageSize).stream()
                        .map(
                            i -> {
                              Suggest s = new Suggest();
                              s.data = i;
                              s.type = "category";
                              return s;
                            })
                        .toList()));

    CompletableFuture.allOf(teacherF, courseF, depart, category).join();

    return data.values().stream().flatMap(Collection::stream).toList();
  }
}
