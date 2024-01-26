package com.xhpolaris.meowpick.trigger.http;

import com.xhpolaris.meowpick.common.consts.Consts;
import com.xhpolaris.meowpick.domain.user.model.entity.UserCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.UserVO;
import com.xhpolaris.meowpick.domain.user.service.UserServer;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Tag(name = "UserApi", description = "用户接口")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServer service;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/add")
    public UserVO add(@RequestBody UserCmd.CreateCmd cmd){
        System.out.println("add");
        System.out.println(cmd.getName());
        System.out.println(cmd.getAvatar());
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
