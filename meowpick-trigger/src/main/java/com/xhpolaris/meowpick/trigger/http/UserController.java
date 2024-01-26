package com.xhpolaris.meowpick.trigger.http;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "UserApi", description = "用户接口")
@RequestMapping("/api/user")
public class UserController {

    @PostMapping
    String get(@RequestBody UserCmd.CreateCmd cmd) {
        return "user";
    }
}
