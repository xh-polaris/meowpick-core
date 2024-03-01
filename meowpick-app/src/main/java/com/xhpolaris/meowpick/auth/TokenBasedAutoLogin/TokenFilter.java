package com.xhpolaris.meowpick.auth.TokenBasedAutoLogin;

import com.xhpolaris.meowpick.auth.AbstractSecurityFilter;
import com.xhpolaris.meowpick.auth.MeowAuthenticationToken;
import com.xhpolaris.meowpick.domain.comment.service.ReplyServer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenFilter extends OncePerRequestFilter {

    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
//                                                                                              IOException,
//                                                                                              ServletException {
//        doFilterInternal((HttpServletRequest) request, (HttpServletResponse) response, chain);
//    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
                                                                                                                       ServletException,
                                                                                                                       IOException {
        String token = request.getHeader("token");
        if (StringUtils.hasText(token)) {

            System.out.println(token);
            SecurityContextHolder.getContext()
                                 .setAuthentication(MeowAuthenticationToken.authorized("1", "eicen", true));
        }

        filterChain.doFilter(request, response);
    }
}
