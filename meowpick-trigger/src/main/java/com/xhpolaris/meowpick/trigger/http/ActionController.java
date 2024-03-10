package com.xhpolaris.meowpick.trigger.http;

import com.xhpolaris.meowpick.api.ActionApi;
import com.xhpolaris.meowpick.common.properties.WeappProperties;
import com.xhpolaris.meowpick.common.utils.Meowpick;
import com.xhpolaris.meowpick.domain.user.model.entity.ActionCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.WeappVO;
import com.xhpolaris.meowpick.domain.user.service.ActionServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ActionController implements ActionApi {
    private final ActionServer    service;
    private final RestTemplate    restTemplate;
    private final WeappProperties props;

    @Override
    public boolean like(String id, ActionCmd.CreateCmd cmd) {
        return service.like(id, cmd);
    }

    @Override
    public WeappVO weapp_openid(String id) {
        log.info("props {}", props);
        String code2SessionRespStr = restTemplate.getForObject(props.getUrl(), String.class,
                props.getAppid(), props.getSecret(), id);

        log.info(code2SessionRespStr);
        WeappVO.WxResponse response = Meowpick.fromJson(code2SessionRespStr, WeappVO.WxResponse.class);

        WeappVO vo = new WeappVO();

        vo.setKey(UUID.randomUUID().toString());
        vo.setOpenid(response.getUnionid());

        return vo;
    }
}
