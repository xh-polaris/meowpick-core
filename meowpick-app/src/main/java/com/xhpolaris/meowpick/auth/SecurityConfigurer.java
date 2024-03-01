package com.xhpolaris.meowpick.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("all")
public class SecurityConfigurer {
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;
    private RememberMeServices rememberMeServices;
    private HttpSecurity http;

    private AuthenticationProvider provider;
    private AbstractSecurityFilter filter;


    protected void init(HttpSecurity builder) throws Exception {
        if (provider != null) {
            builder.authenticationProvider(provider);
        }
    }

    public void configure(HttpSecurity builder) throws Exception {
        this.http = builder;
        init(builder);

        if (filter != null) {
            filter.setAuthenticationManager(new ProviderManager(provider));
            if (this.successHandler != null) {
                filter.setAuthenticationSuccessHandler(successHandler);
            }
            if (this.failureHandler != null) {
                filter.setAuthenticationFailureHandler(failureHandler);
            }

            filter = postProcess(filter);
            addFilter(filter);
        }
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

    public SecurityConfigurer provider(AuthenticationProvider provider) {
        this.provider = provider;
        return this;
    }

    public SecurityConfigurer filter(AbstractSecurityFilter filter) {
        this.filter = filter;
        return this;
    }

}
