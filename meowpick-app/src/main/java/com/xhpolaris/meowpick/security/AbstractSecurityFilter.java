package com.xhpolaris.meowpick.security;

import com.xhpolaris.meowpick.common.utils.RequestJsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
                                                HttpServletResponse response)
    throws AuthenticationException {
        postAuthentication(request);

        return getAuthenticationManager().authenticate(buildToken(request));
    }

    protected Authentication buildToken(HttpServletRequest request) {
        return null;
    }

    @SneakyThrows
    protected String obtainParameter(String parameter) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String             type    = request.getContentType();
        if (MediaType.APPLICATION_JSON_VALUE.equals(type)) {
            return RequestJsonUtils.getRequestJsonObject(request).getString(parameter);
        }

        return request.getParameter(parameter);
    }

    protected String obtainHeader(String parameter) {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                                                                                       .getHeader(parameter);
    }

    protected void postAuthentication(HttpServletRequest request) {
//        throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
    }
}
