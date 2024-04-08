package com.xhpolaris.meowpick.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Base64;

public final class Meowpick {
    private Meowpick() {}

    private static final Gson gson = new GsonBuilder().create();

    public static String toJson(Object data) {
        return gson.toJson(data);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static byte[] parsePublicKeyString(String publicKeyString) {
        // 去除开头和结尾的标记，并移除换行符
        String base64PublicKey = publicKeyString
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        // 将 Base64 编码的字符串解码为字节数组
        return Base64.getDecoder().decode(base64PublicKey);
    }
}
