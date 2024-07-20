package com.xhpolaris.meowpick.trigger.http.api;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.PopularityCmd;
import com.xhpolaris.meowpick.domain.model.valobj.SearchCmd;
import com.xhpolaris.meowpick.domain.model.valobj.SearchHistoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "SearchController", description = "搜索接口")
@RequestMapping("/api/search")
public interface SearchApi {

  @Operation(summary = "猜你想搜")
  @GetMapping("/guess")
  PageEntity<String> guess(PopularityCmd.Query query);

  @Operation(summary = "最近搜过")
  @GetMapping("/recent")
  List<SearchHistoryVO> recent();

  @Operation(summary = "删除搜索历史")
  @PostMapping("/recent/remove/{id}")
  boolean removeRecent(@PathVariable String id);

  @Operation(summary = "搜索建议")
  @GetMapping("/suggest")
  List<?> suggest(
      @RequestParam(required = true) String search,
      @RequestParam(defaultValue = "1", required = false) Integer pageNum,
      @RequestParam(defaultValue = "10", required = false) Integer pageSize);

  @PostMapping
  PageEntity<?> search(@Validated @RequestBody SearchCmd.Query query);

  @Operation(summary = "获取吐槽总数")
  @GetMapping("/total")
  Integer total();
}
