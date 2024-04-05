package com.xhpolaris.meowpick;

import com.xhpolaris.idlgen.meowchat.content.ContentServiceGrpc;
import com.xhpolaris.idlgen.meowchat.content.ListPostReq;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@EnableCaching
@RestController
@EnableScheduling
@SpringBootApplication
@EnableMongoAuditing
@EnableWebSecurity
@EnableConfigurationProperties
public class MeowpickApplication {
    @Value("${server.servlet.context-path}")
    String path;

    public static void main(String[] args) {
        SpringApplication.run(MeowpickApplication.class, args);
    }

    @GetMapping
    public void doc(HttpServletResponse response) throws IOException {
        response.sendRedirect(String.format("%s/doc.html", path));
    }
}