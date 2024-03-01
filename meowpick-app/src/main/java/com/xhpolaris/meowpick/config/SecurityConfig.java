package com.xhpolaris.meowpick.config;

import com.google.gson.Gson;
import com.xhpolaris.meowpick.auth.TokenBasedAutoLogin.TokenFilter;
import com.xhpolaris.meowpick.auth.WeappAutoLogin.WxOpenIdConfigurer;
import com.xhpolaris.meowpick.common.JsonRet;
import com.xhpolaris.meowpick.auth.AbstractSecurityConfigurer;
import com.xhpolaris.meowpick.common.enums.HttpStateEn;
import com.xhpolaris.meowpick.domain.user.model.valobj.TokenVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Configuration
@SuppressWarnings("all")
public class SecurityConfig {
    private final Gson gson;
    protected final List<AbstractSecurityConfigurer> configurers;

    protected List<String> canPermitAntPatterns = new ArrayList<>();

    public SecurityConfig(Gson gson, List<AbstractSecurityConfigurer> configurers) {
        this.gson = gson;
        this.configurers = configurers;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        config(http);

        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(r -> r.requestMatchers(canPermitAntPatterns.toArray(new String[0]))
                                         .permitAll()
                                         .anyRequest()
                                         .authenticated());

        http.exceptionHandling(d -> d.accessDeniedHandler((req, res, ex) -> {
            fail(res, HttpStateEn.unauthorized);
        }).authenticationEntryPoint((req, res, ex) -> {
            fail(res, HttpStateEn.un_login);
        }));

        return http.build();
    }

    private void fail(HttpServletResponse response, HttpStateEn stateEn) throws
                                                                         IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.write(gson.toJson(JsonRet.fail(stateEn)));
        out.close();
    }

    protected void config(HttpSecurity http) throws Exception {
        http.addFilterBefore(new TokenFilter(), UsernamePasswordAuthenticationFilter.class);
        for (var configurer : configurers) {
            configurer.configure(http);
        }
    }

    protected void initPermitAntPatterns() {
        this.canPermitAntPatterns.addAll(List.of("/napi/internal/**", "/napi/public/**", "/account/**", "/error", "/health"));
    }
}
