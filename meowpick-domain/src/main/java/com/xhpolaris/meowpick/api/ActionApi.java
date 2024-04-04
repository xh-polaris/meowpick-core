package com.xhpolaris.meowpick.api;

import com.xhpolaris.meowpick.domain.user.model.entity.ActionCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.WeappVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "ActionApi", description = "用户行为接口")
@RequestMapping("/api/action")
public interface ActionApi {

    @PostMapping("/like/{id}")
    @Operation(summary = "点赞")
    boolean like(@PathVariable String id, @RequestBody ActionCmd.CreateCmd cmd);
}
