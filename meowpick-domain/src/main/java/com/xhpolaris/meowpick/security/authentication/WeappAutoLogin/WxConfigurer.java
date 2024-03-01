package com.xhpolaris.meowpick.security.authentication.WeappAutoLogin;

import com.google.gson.Gson;
import com.xhpolaris.meowpick.common.JsonRet;
import com.xhpolaris.meowpick.domain.user.model.valobj.TokenVO;
import com.xhpolaris.meowpick.domain.user.model.valobj.UserVO;
import com.xhpolaris.meowpick.security.AbstractSecurityFilter;
import com.xhpolaris.meowpick.security.SecurityConfigurer;
import com.xhpolaris.meowpick.security.authentication.MeowAuthenticationToken;
import com.xhpolaris.meowpick.security.authorize.MeowUser;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WxConfigurer extends SecurityConfigurer {
    private final Gson gson;

    @Override
    protected void postInit(List<AuthenticationProvider> providers) {
        providers.add(new WxOpenIdProvider());
    }

    @Override
    protected AbstractSecurityFilter buildFilter() {
        this.successHandler((req, res, auth) -> {
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
            vo.setToken("login token can use");

            then(res, vo);
        });
        return new WxOpenIdFilter();
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
