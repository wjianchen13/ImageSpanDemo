package com.example.imagespandemo.at;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Gson工具类
 */
public class CommonGsonUtils {

    private static final String TAG = CommonGsonUtils.class.getSimpleName();

    public static String toJson(Object target) {
        String result = "{}";
        if (target == null) {
            return result;
        }
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            result = gson.toJson(target);
        } catch (Exception ex) {
//            LogUtils.i(TAG, "Gson toJson：" + ex.getMessage());
            if (target instanceof Collection<?> || target instanceof Iterator<?> || target instanceof Enumeration<?> || target.getClass().isArray()) {
                result = "[]";
            }
        }
        return result;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception ex) {
//            LogUtils.i(TAG, json + "Gson exception：" + ex.getMessage());
            return null;
        }
    }


    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
     *
     * @param <T>   要转换的目标类型。
     * @param json  给定的 {@code JSON} 字符串。
     * @param token {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     * @since 1.0
     */
    public static <T> T fromJson(String json, TypeToken<T> token) {
        return fromJson(json, token, null);
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
     *
     * @param <T>         要转换的目标类型。
     * @param json        给定的 {@code JSON} 字符串。
     * @param token       {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
     * @param datePattern 日期格式模式。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     * @since 1.0
     */
    public static <T> T fromJson(String json, TypeToken<T> token, String datePattern) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if (TextUtils.isEmpty(datePattern)) {
            datePattern = "yyyy-MM-dd HH:mm:ss SSS";
        }
        builder.setDateFormat(datePattern);
        Gson gson = builder.create();
        try {
            return gson.fromJson(json, token.getType());
        } catch (Exception ex) {
            return null;
        }
    }

}
