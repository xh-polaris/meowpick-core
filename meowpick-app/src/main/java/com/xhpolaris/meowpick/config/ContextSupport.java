package com.xhpolaris.meowpick.config;

import com.xhpolaris.meowpick.domain.service.Context;
import com.xhpolaris.meowpick.domain.model.entity.MeowUser;
import com.xhpolaris.meowpick.common.properties.AppProperties;
import com.xhpolaris.meowpick.trigger.http.security.authorize.MeowAuthenticationToken;
import com.xhpolaris.meowpick.trigger.http.security.authorize.MeowRememberMeAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

import static com.xhpolaris.meowpick.domain.model.entity.MeowUser.anonymous;

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
        return getCurrentUser();
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

    private MeowUser getUser(Authentication auth) {
        if (auth instanceof MeowAuthenticationToken token) {
            return token.getUser();
        } else if (auth instanceof MeowRememberMeAuthenticationToken token) {
            return token.getUser();
        }
        return anonymous();
    }

    private MeowUser getCurrentUser() {
        return getUser(SecurityContextHolder.getContext().getAuthentication());
    }

}
