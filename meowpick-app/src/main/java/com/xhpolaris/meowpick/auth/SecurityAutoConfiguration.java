package com.xhpolaris.meowpick.auth;

import com.google.gson.Gson;
import com.xhpolaris.meowpick.auth.WeappAutoLogin.WxOpenIdFilter;
import com.xhpolaris.meowpick.auth.WeappAutoLogin.WxOpenIdProvider;
import com.xhpolaris.meowpick.common.JsonRet;
import com.xhpolaris.meowpick.domain.user.model.valobj.TokenVO;
import com.xhpolaris.meowpick.domain.user.model.valobj.UserVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@RequiredArgsConstructor
public class SecurityAutoConfiguration {
    private final Gson gson;

    @Bean
    SecurityConfigurer weapp() {
        return new SecurityConfigurer()
                .filter(new WxOpenIdFilter())
                .provider(new WxOpenIdProvider())
                .successHandler((req, res, auth) -> {
                    MeowUser user = null;
                    if (auth instanceof MeowAuthenticationToken) {
                        user = ((MeowAuthenticationToken) auth).getUser();
                    }

                    TokenVO vo = new TokenVO();

                    UserVO uservo = new UserVO();
                    if (user != null) {
                        uservo.setId(user.getUserId());
                        uservo.setName(user.getUsername());
                    }

                    vo.setUser(uservo);
                    vo.setToken("token");

                    then(res, vo);
                });
    }

    private void then(HttpServletResponse response, Object data) throws
                                                                 IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.write(gson.toJson(JsonRet.then(data)));
        out.close();
    }
}
