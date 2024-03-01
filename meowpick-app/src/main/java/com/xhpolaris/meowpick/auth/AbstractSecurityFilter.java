package com.xhpolaris.meowpick.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@SuppressWarnings("all")
public abstract class AbstractSecurityFilter extends AbstractAuthenticationProcessingFilter {
    protected AbstractSecurityFilter() {
        super(new AntPathRequestMatcher("/account/login", HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws
                                                                              AuthenticationException {
        postAuthentication(request);

        return getAuthenticationManager().authenticate(buildToken(request));
    }

    protected void postAuthentication(HttpServletRequest request) {
//        throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
    }

    protected Authentication buildToken(HttpServletRequest request) {
        return null;
    }

    protected String obtainParameter(String parameter) {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getParameter(parameter);
    }

    protected String obtainHeader(String parameter) {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(parameter);
    }
}
