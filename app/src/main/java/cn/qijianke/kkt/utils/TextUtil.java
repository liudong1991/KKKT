package cn.qijianke.kkt.utils;

import android.text.TextUtils;

/**
 * Created by Administrator on 2015/11/4.
 */
public class TextUtil {

    public static String numFormat(String num) {
        if (!TextUtils.isEmpty(num)) {
            float f = Float.valueOf(num);
            if (f == (int) f) {
                num = (int) f + "";
            } else if (f * 10 == (int) (f * 10)) {
                num = String.format("%.1f", f);
            } else {
                num = String.format("%.2f", f);
            }
        }
        return num;
    }
}