package com.xhpolaris.meowchat.mall;

import com.xhpolaris.idlgen.meowchat.content.ContentServiceGrpc;
import com.xhpolaris.idlgen.meowchat.content.ListPostReq;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@SpringBootApplication
@ServletComponentScan
@EnableConfigurationProperties
@EnableScheduling
public class MallApplication {
    @GrpcClient("meowchat-content")
    private ContentServiceGrpc.ContentServiceBlockingStub meowchatContentService;

    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }

    @RequestMapping
    public String hello() {
        var res = this.meowchatContentService.listPost(ListPostReq.newBuilder().build());
        System.out.println(res);
        return "";
    }
}