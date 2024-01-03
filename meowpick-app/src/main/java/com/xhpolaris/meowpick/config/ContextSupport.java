package com.xhpolaris.meowpick.config;

import com.xhpolaris.meowpick.common.Context;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class ContextSupport implements Context, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void publish(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

    @Override
    public void setApplicationContext(
            @Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
