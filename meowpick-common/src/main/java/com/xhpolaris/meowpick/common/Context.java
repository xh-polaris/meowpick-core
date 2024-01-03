package com.xhpolaris.meowpick.common;

import org.springframework.context.ApplicationEvent;

public interface Context {
    void publish(ApplicationEvent event);
}
