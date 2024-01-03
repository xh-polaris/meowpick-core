package com.xhpolaris.meowpick.trigger.http.api;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.search.model.entity.SearchCmd;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "SearchController", description = "搜索接口")
@RequestMapping("/api/search")
public interface SearchApi {

    @Operation(summary = "猜你想搜")
    @GetMapping("/guess")
    String guess();

    @Operation(summary = "最近搜过")
    @GetMapping("/recent")
    List<?> recent();

    @Operation(summary = "搜索建议")
    @GetMapping("/suggest")
    List<?> suggest();

    @GetMapping
    PageEntity<?> search(@Validated @ParameterObject SearchCmd.Query query);
}
