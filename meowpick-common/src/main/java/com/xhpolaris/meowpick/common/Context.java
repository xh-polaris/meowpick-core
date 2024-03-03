package com.xhpolaris.meowpick.common;

import com.xhpolaris.meowpick.common.authorize.MeowUser;
import com.xhpolaris.meowpick.common.properties.AppProperties;
import org.springframework.context.ApplicationEvent;

public interface Context {
    void publish(ApplicationEvent event);

    AppProperties properties();

    MeowUser getUser();

    String uid();
}
