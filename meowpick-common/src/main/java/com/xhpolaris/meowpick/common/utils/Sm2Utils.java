//package com.xhpolaris.meowpick.common.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
//import org.bouncycastle.crypto.CipherParameters;
//import org.bouncycastle.crypto.InvalidCipherTextException;
//import org.bouncycastle.crypto.digests.SM3Digest;
//import org.bouncycastle.crypto.engines.SM2Engine;
//import org.bouncycastle.crypto.params.ParametersWithRandom;
//import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.util.encoders.DecoderException;
//
//import java.security.InvalidKeyException;
//import java.security.KeyFactory;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.NoSuchAlgorithmException;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.Security;
//import java.security.Signature;
//import java.security.SignatureException;
//import java.security.spec.ECGenParameterSpec;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//
///**
// * SM2工具类
// */
//@Slf4j
//public class Sm2Utils {
//
//    public static String sign(String plainText, String privateKeyStr) {
//        BouncyCastleProvider provider = new BouncyCastleProvider();
//        try {
//            // 获取椭圆曲线KEY生成器
//            KeyFactory keyFactory = KeyFactory.getInstance("EC", provider);
//            byte[] privateKeyData = Base64.getDecoder().decode(privateKeyStr);
//            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyData);
//            Signature rsaSignature = Signature.getInstance(GMObjectIdentifiers.sm2sign_with_sm3.toString(), provider);
//            rsaSignature.initSign(keyFactory.generatePrivate(privateKeySpec));
//            rsaSignature.update(plainText.getBytes());
//            byte[] signed = rsaSignature.sign();
//            return Base64.getEncoder().encodeToString(signed);
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static boolean verify(String plainText, String signatureValue, String publicKeyStr) {
//        BouncyCastleProvider provider = new BouncyCastleProvider();
//        try {
//            // 获取椭圆曲线KEY生成器
//            KeyFactory keyFactory = KeyFactory.getInstance("EC", provider);
//            byte[] publicKeyData = Base64.getDecoder().decode(publicKeyStr);
//            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyData);
//            // 初始化为验签状态
//            Signature signature = Signature.getInstance(GMObjectIdentifiers.sm2sign_with_sm3.toString(), provider);
//            signature.initVerify(keyFactory.generatePublic(publicKeySpec));
//            signature.update(Hex.decodeHex(plainText.toCharArray()));
//            return signature.verify(Hex.decodeHex(signatureValue.toCharArray()));
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalArgumentException | DecoderException e) {
//            log.error("验签失败", e);
//            return false;
//        }
//    }
//
//    public static byte[] encrypt(String plainText, String publicKeyStr) {
//        Security.addProvider(new BouncyCastleProvider());
//        try {
//            // 获取椭圆曲线KEY生成器
//            KeyFactory keyFactory = KeyFactory.getInstance("EC");
//            byte[] publicKeyData = Base64.getDecoder().decode(publicKeyStr);
//            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyData);
//            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
//            CipherParameters publicKeyParamerters = ECUtil.generatePublicKeyParameter(publicKey);
//            //数据加密
//            StandardSM2Engine engine = new StandardSM2Engine(new SM3Digest(), SM2Engine.Mode.C1C3C2);
//            engine.init(true, new ParametersWithRandom(publicKeyParamerters));
//            return engine.processBlock(plainText.getBytes(), 0, plainText.getBytes().length);
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException
//                | InvalidKeyException | InvalidCipherTextException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static String decrypt(byte[] encryptedData, String privateKeyStr) {
//        Security.addProvider(new BouncyCastleProvider());
//        try {
//            // 获取椭圆曲线KEY生成器
//            KeyFactory keyFactory = KeyFactory.getInstance("EC");
//            byte[] privateKeyData = Base64.getDecoder().decode(privateKeyStr);
//            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyData);
//            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
//            CipherParameters privateKeyParamerters = ECUtil.generatePrivateKeyParameter(privateKey);
//            //数据解密
//            StandardSM2Engine engine = new StandardSM2Engine(new SM3Digest(), SM2Engine.Mode.C1C3C2);
//            engine.init(false, privateKeyParamerters);
//            byte[] plainText = engine.processBlock(encryptedData, 0, encryptedData.length);
//            return new String(plainText);
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException
//                | InvalidKeyException | InvalidCipherTextException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * SM2算法生成密钥对
//     * @return 密钥对信息
//     */
//    public static KeyPair generateSm2KeyPair() {
//        try {
//            final ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");
//            // 获取一个椭圆曲线类型的密钥对生成器
//            final KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
//            SecureRandom random = new SecureRandom();
//            // 使用SM2的算法区域初始化密钥生成器
//            kpg.initialize(sm2Spec, random);
//            // 获取密钥对
//            return kpg.generateKeyPair();
//        } catch (Exception e) {
//            log.error("generate sm2 key pair failed:{}", e.getMessage(), e);
//            return null;
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        KeyPair keyPair = generateSm2KeyPair();
//        assert keyPair != null;
//        String  privateKey       = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
//        String  publicKey        = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
//        String  data             = "{\"daId\":\"123456\"}";
//        String  encryptedJsonStr = Hex.encodeHexString(encrypt(data, publicKey));//16进制字符串
//        String  decryptedJsonStr = decrypt(Hex.decodeHex(encryptedJsonStr), privateKey);
//        String  sign             = Hex.encodeHexString(Base64.getDecoder().decode(sign(data, privateKey)));
//        boolean flag             = verify(Hex.encodeHexString(data.getBytes()), sign, publicKey);
//        System.out.println("base64后privateKey:" + privateKey);
//        System.out.println("base64后publicKey:" + publicKey);
//        System.out.println("加密前数据:" + data);
//        System.out.println("公钥加密后16进制字符串:" + encryptedJsonStr);
//        System.out.println("私钥解密后数据：" + decryptedJsonStr);
//        System.out.println("私钥加签后数据(16进制)：" + sign);
//        System.out.println("公钥验签结果：" + flag);
//
//    }
//
//}