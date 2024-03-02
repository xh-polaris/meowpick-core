package com.xhpolaris.meowpick.config;

import com.xhpolaris.meowpick.common.Context;
import com.xhpolaris.meowpick.common.properties.AppProperties;
import com.xhpolaris.meowpick.common.security.authorize.MeowUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContextSupport implements Context, ApplicationContextAware {
    private       ApplicationContext applicationContext;
    private final AppProperties      properties;

    @Override
    public void publish(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

    @Override
    public AppProperties properties() {
        return properties;
    }

    @Override
    public MeowUser getUser() {
        return Optional.ofNullable(MeowUser.getCurrentUser())
                       .orElse(MeowUser.anonymous());
    }

    @Override
    public String uid() {
        return getUser().getUserId();
    }

    @Override
    public void setApplicationContext(
            @Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
