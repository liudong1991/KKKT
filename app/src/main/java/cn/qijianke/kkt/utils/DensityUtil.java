package cn.qijianke.kkt.utils;


import android.util.TypedValue;

import cn.qijianke.kkt.KKTApplication;

public class DensityUtil {


    public static int dp2px(float dpValue) {
        final float density = KKTApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public static int sp2px(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, KKTApplication.getInstance().getResources().getDisplayMetrics());
    }
}
