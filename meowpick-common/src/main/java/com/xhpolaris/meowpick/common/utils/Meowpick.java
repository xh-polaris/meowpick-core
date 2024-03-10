package com.xhpolaris.meowpick.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Meowpick {
    private Meowpick() {}

    private static final Gson gson = new GsonBuilder().create();

    public static String toJson(Object data) {
        return gson.toJson(data);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
