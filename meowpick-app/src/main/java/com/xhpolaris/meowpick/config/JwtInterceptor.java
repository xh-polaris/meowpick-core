package com.xhpolaris.meowpick.config;


import com.xhpolaris.idlgen.basic.UserMeta;
import com.xhpolaris.meowpick.common.consts.Consts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        System.out.println(token);

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(Consts.PUBLIC_KEY)
                    .parseClaimsJws(token);

            String subject = claims.getBody().getSubject();
            System.out.println("Subject: "+ subject);

            String userId = (String)claims.getBody().get("userId");
            System.out.println(userId);

//            DecodedJWT verify = JWT.require(Algorithm.ECDSA256(getPublicKey(Consts.publicKey))).build().verify(token);
//            verify.getClaim("userId").as(UserMeta.class)
//            System.out.println(verify.getClaim("userId").asString());
//            System.out.println(verify.getClaim("accessToken").asString());
//            System.out.println(verify.getClaim("accessExpire").asString());

            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("token invalid");
        }

        return false;
    }

//    private PublicKey getPublicKey(String publicKeyStr) throws Exception {
//        byte[] publicBytes = Base64.getDecoder().decode(publicKeyStr);
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance("EC");
//        return keyFactory.generatePublic(keySpec);
//    }

}
