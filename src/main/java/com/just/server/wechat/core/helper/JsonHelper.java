package com.just.server.wechat.core.helper;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 13:06
 * Description: Json帮助类
 */
public class JsonHelper {

    private static Gson gson = new Gson();

    private JsonHelper() {

    }

    public static Gson getGson() {
        return gson;
    }
}
