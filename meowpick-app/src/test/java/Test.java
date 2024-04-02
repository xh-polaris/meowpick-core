import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;

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
        String jwtToken = "eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6MCwiZGV2aWNlSWQiOiIiLCJleHAiOjE3MTQ2Njg3MzMsImlhdCI6MTcxMjA3NjczMywidXNlcklkIjoiNjNmNGZhZDI0NzNhNmI3Y2YyNzYwM2M2Iiwid2VjaGF0VXNlck1ldGEiOnt9fQ.kO6gTvqZhaz4f0ci87lW_a7qu0jQgupCe1KHzcYqBulMRD7rBN9FR-_nk2KdInN98bu-UL6Iw8J9QrChcGNG0g";
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
            } catch (JWTDecodeException e) {
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
