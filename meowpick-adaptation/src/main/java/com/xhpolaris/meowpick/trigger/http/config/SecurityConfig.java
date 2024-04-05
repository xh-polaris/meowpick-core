package com.xhpolaris.meowpick.trigger.http.config;

import com.xhpolaris.meowpick.common.JsonRet;
import com.xhpolaris.meowpick.trigger.http.security.authorize.MeowUserDetailService;
import com.xhpolaris.meowpick.common.consts.Consts;
import com.xhpolaris.meowpick.common.enums.HttpStateEn;
import com.xhpolaris.meowpick.trigger.http.utils.RequestJsonUtils;
import com.xhpolaris.meowpick.trigger.http.security.SecurityConfigurer;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@SuppressWarnings("all")
@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "app.security.enable",
        havingValue = "true"
)
public class SecurityConfig {

    protected final List<SecurityConfigurer> configurers;
    protected       List<String>             canPermitAntPatterns = new ArrayList<>();
    private final   MeowUserDetailService    userDetailsService;
    private final   RememberMeServices       rememberMeServices;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
    throws Exception {
        log.info("start security config");
        config(http);

        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        http.rememberMe(r -> r.rememberMeServices(rememberMeServices).key(Consts.Authorize.KEY));
        http.authorizeHttpRequests(r -> r.requestMatchers(canPermitAntPatterns.toArray(new String[0]))
                                         .permitAll()
                                         .anyRequest()
                                         .authenticated()
                                  );

        http.exceptionHandling(d -> d.accessDeniedHandler((req, res, ex) -> {
            fail(res, HttpStateEn.unauthorized);
        }).authenticationEntryPoint((req, res, ex) -> {
            fail(res, HttpStateEn.un_login);
        }));

        return http.build();
    }

    private void fail(HttpServletResponse response, HttpStateEn stateEn)
    throws IOException {
        RequestJsonUtils.write(JsonRet.fail(stateEn));
    }

    protected void config(HttpSecurity http)
    throws Exception {
        initPermitAntPatterns();

        for (var configurer : configurers) {
            http.apply(configurer);
        }
    }

    protected void initPermitAntPatterns() {
        this.canPermitAntPatterns.addAll(List.of(
                "/",
                "/api/action/weapp/**",
                "/webjars/**",
                "/v3/**",
                "/swagger**/**",
                "/doc.html",
                "/napi/internal/**",
                "/napi/public/**",
                "/account/**",
                "/error",
                "/health",
                "/actuator/**"));
    }
}
