package com.xhpolaris.meowpick.auth.TokenBasedAutoLogin;

import com.xhpolaris.meowpick.auth.MeowAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (StringUtils.hasText(token) && token.startsWith("Berry ")) {
            token = token.substring(6);

            System.out.println(token);
            SecurityContextHolder.getContext()
                                 .setAuthentication(MeowAuthenticationToken.authorized("1", "eicen", true));
        }

        filterChain.doFilter(request, response);
    }
}
