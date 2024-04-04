package com.xhpolaris.meowpick.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.protobuf.util.JsonFormat;
import com.xhpolaris.idlgen.basic.UserMeta;
import com.xhpolaris.meowpick.common.JsonRet;
import com.xhpolaris.meowpick.common.authorize.MeowRememberMeAuthenticationToken;
import com.xhpolaris.meowpick.common.authorize.MeowUser;
import com.xhpolaris.meowpick.common.authorize.MeowUserDetailService;
import com.xhpolaris.meowpick.common.consts.Consts;
import com.xhpolaris.meowpick.common.properties.AppProperties;
import com.xhpolaris.meowpick.common.utils.Meowpick;
import com.xhpolaris.meowpick.common.utils.RequestJsonUtils;
import com.xhpolaris.meowpick.common.utils.Sm2Utils;
import com.xhpolaris.meowpick.infrastructure.mapstruct.UserMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class MeowpickTokenBasedRememberMeService extends NullRememberMeServices {
    public static final  int    Token_WEEKS_S = Consts.TOKEN_WEEKS;
    private static final String DELIMITER     = Consts.SPLIT;

    private final AppProperties         appProperties;
    private final MeowUserDetailService userDetailsService;
    private final UserDetailsChecker    userDetailsChecker = new AccountStatusUserDetailsChecker();

    protected MeowUser retrieveUser(UserMeta userMeta) {
        if (userMeta == null) {
            return MeowUser.anonymous();
        }

        return userDetailsService.loadUserById(userMeta.getUserId());
    }

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        String rememberMeToken = extractRememberMeToken(request);
        if (rememberMeToken == null || rememberMeToken.isEmpty()) {
            return null;
        }

        UserMeta tokens = decodeToken(rememberMeToken);
        MeowUser user   = retrieveUser(tokens);
        this.userDetailsChecker.check(user);

        return createSuccessFulAuthentication(user);
    }

    protected Authentication createSuccessFulAuthentication(MeowUser user) {
        return MeowRememberMeAuthenticationToken.authorized(user);
    }

    @SneakyThrows
    protected UserMeta decodeToken(String rememberMeToken) {

        byte[] publicKeyBytes = Meowpick.parsePublicKeyString(appProperties.getPublicKey());

        // 转换为公钥对象
        try {
            KeyFactory         keyFactory = KeyFactory.getInstance("EC");
            X509EncodedKeySpec keySpec    = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey          publicKey  = keyFactory.generatePublic(keySpec);

            // 验证 JWT
            DecodedJWT decodedJWT = JWT.require(Algorithm.ECDSA256((ECPublicKey) publicKey, null))
                                       .build()
                                       .verify(rememberMeToken);
            log.info("JWT verification successful!");
            String string = new String(Base64.getDecoder().decode(decodedJWT.getPayload()));
            Meowpick.fromJson(string, UserMeta.class);

            UserMeta.Builder builder = UserMeta.newBuilder();
            JsonFormat.parser().ignoringUnknownFields().merge(string, builder);

            return builder.build();
        } catch (Exception e) {
            return null;
        }
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
