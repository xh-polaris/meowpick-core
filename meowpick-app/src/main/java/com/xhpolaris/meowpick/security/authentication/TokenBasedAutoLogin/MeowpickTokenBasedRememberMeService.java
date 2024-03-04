package com.xhpolaris.meowpick.security.authentication.TokenBasedAutoLogin;

import com.xhpolaris.meowpick.common.JsonRet;
import com.xhpolaris.meowpick.common.authorize.MeowRememberMeAuthenticationToken;
import com.xhpolaris.meowpick.common.authorize.MeowUser;
import com.xhpolaris.meowpick.common.authorize.MeowUserDetailService;
import com.xhpolaris.meowpick.common.consts.Consts;
import com.xhpolaris.meowpick.common.utils.RequestJsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class MeowpickTokenBasedRememberMeService extends NullRememberMeServices {
    public static final  int            TWO_WEEKS_S = 1209600;
    private static final String         DELIMITER   = ":";

    private final MeowUserDetailService userDetailsService;
    private final UserDetailsChecker    userDetailsChecker = new AccountStatusUserDetailsChecker();

    private Collection<? extends GrantedAuthority> mapAuthorities(UserDetails user) {
        return user.getAuthorities();
    }

    protected MeowUser retrieveUser(String[] tokens) {
        String id         = tokens[0];
        long   expiryTime = getTokenExpiryTime(tokens[1]);
        String signature  = tokens[2];

        if (expiryTime < System.currentTimeMillis()) {
        }

        MeowUser user = userDetailsService.loadUserById(id);

        String expectedTokenSignature = makeTokenSignature(user, expiryTime);
        if (!expectedTokenSignature.equals(signature)) {
        }

        return user;
    }

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        String rememberMeToken = extractRememberMeToken(request);
        if (rememberMeToken == null || rememberMeToken.isEmpty()) {
            return null;
        }

        String[] tokens = decodeToken(rememberMeToken);
        MeowUser user   = retrieveUser(tokens);
        this.userDetailsChecker.check(user);

        return createSuccessFulAuthentication(user);
    }

    protected Authentication createSuccessFulAuthentication(MeowUser user) {
        return MeowRememberMeAuthenticationToken.authorized(user);
    }

    @SneakyThrows
    protected String[] decodeToken(String rememberMeToken) {
        String cookieAsPlainText = new String(Base64.getDecoder().decode(rememberMeToken), StandardCharsets.UTF_8);

        System.out.println(cookieAsPlainText);
        String[] tokens = StringUtils.delimitedListToStringArray(cookieAsPlainText, DELIMITER);
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = URLDecoder.decode(tokens[i], StandardCharsets.UTF_8);
        }
        return tokens;
    }

    private String encodeToken(String[] tokens) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            sb.append(URLEncoder.encode(tokens[i], StandardCharsets.UTF_8));
            if (i < tokens.length - 1) {
                sb.append(DELIMITER);
            }
        }

        System.out.println(sb);
        return Base64.getEncoder().encodeToString(sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    private String makeTokenSignature(MeowUser user, long expiryTime) {
        return Consts.Authorize.KEY;
    }

    protected String getToken(MeowUser user) {
        long expiryTime = System.currentTimeMillis();
        expiryTime += 1000L * TWO_WEEKS_S;

        String signatureValue = makeTokenSignature(user, expiryTime);

        return encodeToken(new String[]{
                user.getUserId(),
                Long.toString(expiryTime),
                signatureValue
        });
    }

    private long getTokenExpiryTime(String time) {
        try {
            return Long.parseLong(time);
        } catch (NumberFormatException nfe) {
            throw new InvalidCookieException(
                    "token[1] did not contain a valid number (contained '" + time + "')");
        }
    }

    private String extractRememberMeToken(HttpServletRequest request) {
        String berryToken = request.getHeader("token");
        if (StringUtils.hasText(berryToken) && berryToken.startsWith("Berry ")) {
            return berryToken.substring(6);
        }

        return null;
    }

    @Override
    public void loginSuccess(HttpServletRequest request,
                             HttpServletResponse response,
                             Authentication successfulAuthentication) {
        System.out.println(successfulAuthentication);
        MeowUser user = MeowUser.getUser(successfulAuthentication);

        Response resp = new Response();

        resp.setToken(getToken(user));

        RequestJsonUtils.write(JsonRet.then(resp));
    }

    @Data
    static class Response {
        private String token;
    }
}
