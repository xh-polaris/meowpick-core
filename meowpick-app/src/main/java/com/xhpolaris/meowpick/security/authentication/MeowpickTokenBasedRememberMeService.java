package com.xhpolaris.meowpick.security.authentication;

import com.xhpolaris.meowpick.common.JsonRet;
import com.xhpolaris.meowpick.common.authorize.MeowRememberMeAuthenticationToken;
import com.xhpolaris.meowpick.common.authorize.MeowUser;
import com.xhpolaris.meowpick.common.authorize.MeowUserDetailService;
import com.xhpolaris.meowpick.common.consts.Consts;
import com.xhpolaris.meowpick.common.properties.AppProperties;
import com.xhpolaris.meowpick.common.utils.RequestJsonUtils;
import com.xhpolaris.meowpick.common.utils.Sm2Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class MeowpickTokenBasedRememberMeService extends NullRememberMeServices {
    public static final  int                   Token_WEEKS_S      = Consts.TOKEN_WEEKS;
    private static final String                DELIMITER          = Consts.SPLIT;
    private final        UserDetailsChecker    userDetailsChecker = new AccountStatusUserDetailsChecker();
    private final        AppProperties         appProperties;
    private final        MeowUserDetailService userDetailsService;

    protected MeowUser retrieveUser(String[] tokens) {
        String id         = tokens[0];
        long   expiryTime = getTokenExpiryTime(tokens[1]);

        if (expiryTime < System.currentTimeMillis()) {
        }

        return userDetailsService.loadUserById(id);
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
        String tokenAsPlainText = Sm2Utils.decrypt(Hex.decodeHex(rememberMeToken), appProperties.getPrivateKey());

        String[] tokens = StringUtils.delimitedListToStringArray(tokenAsPlainText, DELIMITER);
        tokens = Arrays.stream(tokens).map(token-> URLDecoder.decode(token, StandardCharsets.UTF_8)).toArray(String[]::new);

        return tokens;
    }

    @SneakyThrows
    private String encodeToken(String[] tokens) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            sb.append(URLEncoder.encode(tokens[i], StandardCharsets.UTF_8));
            if (i < tokens.length - 1) {
                sb.append(DELIMITER);
            }
        }

        return Hex.encodeHexString(Sm2Utils.encrypt(sb.toString(), appProperties.getPublicKey()));
    }

    protected String getToken(MeowUser user) {
        long expiryTime = System.currentTimeMillis();
        expiryTime += 1000L * Token_WEEKS_S;

//        String signatureValue = makeTokenSignature(user, expiryTime);

        return encodeToken(new String[]{
                user.getUserId(),
                Long.toString(expiryTime)
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
