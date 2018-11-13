package cn.qijianke.kkt.utils;

import android.content.Context;
import android.content.SharedPreferences;

import cn.qijianke.kkt.KKTApplication;

public class SPUtil {

    private static SharedPreferences sp;

    static {
        sp = KKTApplication.getInstance().getSharedPreferences("kkt", Context.MODE_PRIVATE);
    }

    public static void put(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public static String get(String key) {
        return sp.getString(key, "");
    }

}
