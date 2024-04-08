package com.xhpolaris.meowpick.domain.service;

import com.xhpolaris.meowpick.common.properties.AppProperties;
import com.xhpolaris.meowpick.domain.model.entity.MeowUser;
import org.springframework.context.ApplicationEvent;

public interface Context {
    void publish(ApplicationEvent event);

    AppProperties properties();

    MeowUser getUser();

    String uid();
}
