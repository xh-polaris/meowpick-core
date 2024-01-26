package com.xhpolaris.meowpick.trigger.http;

import com.xhpolaris.meowpick.domain.user.model.entity.UserCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.UserVO;
import com.xhpolaris.meowpick.domain.user.service.UserServer;
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
        log.info("add");
        log.info(cmd.getName());
        log.info(cmd.getAvatar());
//        return service.createUser(cmd);
        return null;
    }

//    @PostMapping("/token")
//    public String getTokenByCode(@RequestBody String code){
//        System.out.println("code: " + code);
//
//        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={APPID}&secret={SECRET}&js_code={code}&grant_type=authorization_code";
//        ResponseEntity<String> res = restTemplate.getForEntity(url, String.class, Consts.APPID, Consts.SECRET, code);
//        if(res.getStatusCode() == HttpStatus.OK){
//            System.out.println(res.getBody());
//
//            return res.getBody();
//        }
//
//        return null;
//    }


}
