package com.xhpolaris.meowpick.common;

import com.xhpolaris.meowpick.common.properties.AppProperties;
import org.springframework.context.ApplicationEvent;

public interface Context {
    void publish(ApplicationEvent event);

    AppProperties properties();

    CurUser getUser();
}
