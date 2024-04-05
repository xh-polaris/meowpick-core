package com.xhpolaris.meowpick.trigger.http.impl;

import com.xhpolaris.meowpick.domain.model.valobj.UserCmd;
import com.xhpolaris.meowpick.domain.model.valobj.UserVO;
import com.xhpolaris.meowpick.domain.service.UserServer;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Tag(name = "UserApi", description = "用户接口")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServer service;

    private final RestTemplate restTemplate;

    @PostMapping("/add")
    public UserVO add(@RequestBody UserCmd.CreateCmd cmd){
        return service.createUser(cmd);
    }

}
