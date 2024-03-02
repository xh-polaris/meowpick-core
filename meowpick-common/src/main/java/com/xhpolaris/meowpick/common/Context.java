package com.xhpolaris.meowpick.common;

import com.xhpolaris.meowpick.common.properties.AppProperties;
import com.xhpolaris.meowpick.common.security.authorize.MeowUser;
import org.springframework.context.ApplicationEvent;

public interface Context {
    void publish(ApplicationEvent event);

    AppProperties properties();

    MeowUser getUser();

    String uid();
}
