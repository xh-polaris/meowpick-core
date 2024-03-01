package com.xhpolaris.meowpick.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("all")
public abstract class AbstractSecurityConfigurer {
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;
    private RememberMeServices rememberMeServices;
    private HttpSecurity http;



    protected void init(HttpSecurity builder) throws Exception {
        AuthenticationProvider provider = buildSecurityProvider();
        if (provider != null) {
            builder.authenticationProvider(provider);
        }
    }

    protected AuthenticationProvider buildSecurityProvider() {
        return null;
    }

    public void configure(HttpSecurity builder) throws Exception {
        this.http = builder;
        init(builder);

        AbstractSecurityFilter filter = buildSecurityFilter(this.rememberMeServices);
        filter.setAuthenticationManager(getAuthenticationManager());
        if (this.successHandler != null) {
            filter.setAuthenticationSuccessHandler(successHandler);
        }
        if (this.failureHandler != null) {
            filter.setAuthenticationFailureHandler(failureHandler);
        }

        filter = postProcess(filter);
        addFilter(filter);
    }

    protected AbstractSecurityFilter postProcess(AbstractSecurityFilter filter) {
        return filter;
    }

    protected AuthenticationManager getAuthenticationManager() {
        return http.getSharedObject(AuthenticationManager.class);
    }

    private void addFilter(AbstractSecurityFilter filter) {
        this.http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    abstract protected AbstractSecurityFilter buildSecurityFilter(RememberMeServices rememberMeServices);

    public AbstractSecurityConfigurer successHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
        return this;
    }

    public AbstractSecurityConfigurer failureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
        return this;
    }

    public AbstractSecurityConfigurer rememberMeServices(RememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
        return this;
    }

}
