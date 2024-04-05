package com.xhpolaris.meowpick;

import com.xhpolaris.idlgen.platform.sts.PhotoCheckReq;
import com.xhpolaris.meowpick.infrastructure.rpc.PlatformSts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class TestGRPC {
    @Autowired
    PlatformSts platformSts;
    @Test
    void rpc(){
        log.info("res: {}", platformSts.photoCheck(PhotoCheckReq.newBuilder().addUrl("https://bugstack.cn/images/roadmap/tutorial/roadmap-ddd-01.png?raw=true").build()));
    }
}
