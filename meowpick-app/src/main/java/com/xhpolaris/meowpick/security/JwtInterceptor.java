package com.xhpolaris.meowpick.security;


import com.xhpolaris.meowpick.common.Context;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final Context context;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        log.info(token);

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(context.properties().getPublicKey())
                    .parseClaimsJws(token);

            String subject = claims.getBody().getSubject();
            log.info("Subject: "+ subject);

            String userId = (String)claims.getBody().get("userId");
            log.info(userId);

//            DecodedJWT verify = JWT.require(Algorithm.ECDSA256(getPublicKey(Consts.publicKey))).build().verify(token);
//            verify.getClaim("userId").as(UserMeta.class)
//            System.out.println(verify.getClaim("userId").asString());
//            System.out.println(verify.getClaim("accessToken").asString());
//            System.out.println(verify.getClaim("accessExpire").asString());

            return true;
        }catch (Exception e){
            log.info("token invalid {}", e.getMessage());
        }

        return false;
    }
}
