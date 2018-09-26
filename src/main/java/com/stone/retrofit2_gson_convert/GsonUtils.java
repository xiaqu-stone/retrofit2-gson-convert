package com.stone.retrofit2_gson_convert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created By: sqq
 * Created Time: 17/9/21 下午6:40.
 * 生成自定义适配器Gson对象的工具类
 */
public class GsonUtils {
    private static Gson gson;

    /**
     * @return 返回 自定义类型适配器的gson对象
     */
    public static Gson getGson() {
        if (gson == null) {
            synchronized (GsonUtils.class) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(String.class, new GsonStringTypeAdapter());
                return gsonBuilder.create();
            }
        }
        return gson;
    }
}
