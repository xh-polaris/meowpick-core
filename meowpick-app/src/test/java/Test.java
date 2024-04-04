import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.google.protobuf.util.JsonFormat;
import com.xhpolaris.idlgen.basic.UserMeta;
import com.xhpolaris.meowpick.common.utils.Meowpick;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Test {
    String PUBLIC_KEY_STRING = "-----BEGIN PUBLIC KEY-----\n" +
            "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAElgqbThwLQHPWjZCGdBSl52tDXUyd\n" +
            "2oOD67oAnTozMzuUYqKNQHtdd1qtvS+xrNVJyCWwm4tbiwiRDvjnBCJSyg==\n" +
            "-----END PUBLIC KEY-----";
    @org.junit.jupiter.api.Test
    void test(){
        String jwtToken = "eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6MTAwLCJkZXZpY2VJZCI6IiIsImV4cCI6MTcxNDgzOTAxNiwiaWF0IjoxNzEyMjQ3MDE2LCJ1c2VySWQiOiI2NDFhNjVjNGIyNTc1OTYwNzU5YTFjODciLCJ3ZWNoYXRVc2VyTWV0YSI6eyJhcHBJZCI6Ind4ZGZmYzQ0NWJkZTMzMTNhYSIsIm9wZW5JZCI6Im80aGVZNG9tWlFzQ1Rub3Vrb2ZWQkhfajN3eEkiLCJ1bmlvbklkIjoib1Y2a1U1OVRVUjVhTUxpdDdQWWFUWGZEYTFPSSJ9fQ.nk-ouhYtRwEXyz_jWUbyr5Yl6kbtPTx646jYjtskiLHHNQihGe9t1yP7XabIX7h2N9L8SOtzAnQaqLnPdrGG5Q";
        // 解码公钥字符串
        byte[] publicKeyBytes = parsePublicKeyString(PUBLIC_KEY_STRING);

        // 转换为公钥对象
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            // 验证 JWT
            try {
                DecodedJWT decodedJWT = JWT.require(Algorithm.ECDSA256((ECPublicKey) publicKey, null))
                        .build()
                        .verify(jwtToken);
                System.out.println("JWT verification successful!");
                String string = new String(Base64.getDecoder().decode(decodedJWT.getPayload()));
                Meowpick.fromJson(string, UserMeta.class);

                UserMeta.Builder builder = UserMeta.newBuilder();
                JsonFormat.parser().ignoringUnknownFields().merge(string, builder);
                UserMeta mate = builder.build();
                System.out.println(mate);
            } catch (Exception e) {
                System.out.println("JWT decoding failed: " + e.getMessage());
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    private byte[] parsePublicKeyString(String publicKeyString) {
        // 去除开头和结尾的标记，并移除换行符
        String base64PublicKey = publicKeyString
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        // 将 Base64 编码的字符串解码为字节数组
        return Base64.getDecoder().decode(base64PublicKey);
    }
}
