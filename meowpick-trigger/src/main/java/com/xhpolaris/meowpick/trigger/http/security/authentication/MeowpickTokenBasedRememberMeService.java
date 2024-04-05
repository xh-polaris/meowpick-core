package com.xhpolaris.meowpick.trigger.http.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.protobuf.util.JsonFormat;
import com.xhpolaris.idlgen.basic.UserMeta;
import com.xhpolaris.meowpick.trigger.http.security.authorize.MeowRememberMeAuthenticationToken;
import com.xhpolaris.meowpick.domain.service.user.MeowUser;
import com.xhpolaris.meowpick.trigger.http.security.authorize.MeowUserDetailService;
import com.xhpolaris.meowpick.common.properties.AppProperties;
import com.xhpolaris.meowpick.common.utils.Meowpick;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class MeowpickTokenBasedRememberMeService extends NullRememberMeServices {
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
        this.userDetailsChecker.check(userDetailsService.of(user));

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

    private String extractRememberMeToken(HttpServletRequest request) {
        String berryToken = request.getHeader("token");
        if (StringUtils.hasText(berryToken) && berryToken.startsWith("Berry ")) {
            return berryToken.substring(6);
        }

        return null;
    }

    @Data
    static class Response {
        private String token;
    }
}
