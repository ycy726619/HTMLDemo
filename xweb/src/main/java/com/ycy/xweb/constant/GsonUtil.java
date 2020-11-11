package com.ycy.xweb.constant;

import com.google.gson.Gson;

/**
 * 2020/11/11
 * ycy
 */
public class GsonUtil {
    private static final Gson gson = new Gson();

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
