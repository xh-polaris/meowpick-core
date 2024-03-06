package com.xhpolaris.meowpick.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("all")
public abstract class SecurityConfigurer extends AbstractHttpConfigurer<SecurityConfigurer, HttpSecurity> {
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;
    private RememberMeServices           rememberMeServices;
    private HttpSecurity                 http;

    private List<AuthenticationProvider> providers = new ArrayList<>();
    private AbstractSecurityFilter       filter;

    @Override
    public void init(HttpSecurity builder)
    throws Exception {
        postInit(providers);
        if (providers.size() != 0) {
            providers.forEach(builder::authenticationProvider);
        }
    }

    @Override
    public void configure(HttpSecurity builder)
    throws Exception {
        this.http = builder;
//        init(builder);
        filter = buildFilter();
        if (filter != null) {
            filter.setAuthenticationManager(getAuthenticationManager());
            if (this.successHandler != null) {
                filter.setAuthenticationSuccessHandler(successHandler);
            }
            if (this.failureHandler != null) {
                filter.setAuthenticationFailureHandler(failureHandler);
            }
            if (this.rememberMeServices != null) {
                filter.setRememberMeServices(this.rememberMeServices);
            }

            filter = postProcess(filter);
            addFilter(filter);
        }
    }

    protected void postInit(List<AuthenticationProvider> providers) {
    }

    protected abstract AbstractSecurityFilter buildFilter();

    protected AbstractSecurityFilter postProcess(AbstractSecurityFilter filter) {
        return filter;
    }

    protected AuthenticationManager getAuthenticationManager() {
        return Optional.ofNullable(http.getSharedObject(AuthenticationManager.class))
                       .orElse(new ProviderManager(providers));
    }

    private void addFilter(AbstractSecurityFilter filter) {
        this.http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    public SecurityConfigurer successHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
        return this;
    }

    public SecurityConfigurer failureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
        return this;
    }

    public SecurityConfigurer rememberMeServices(RememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
        return this;
    }

    public SecurityConfigurer provider(AuthenticationProvider... providers) {
        this.providers = Arrays.asList(providers);
        return this;
    }

    public SecurityConfigurer filter(AbstractSecurityFilter filter) {
        this.filter = filter;
        return this;
    }
}
