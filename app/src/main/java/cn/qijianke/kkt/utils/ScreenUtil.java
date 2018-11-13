package cn.qijianke.kkt.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import cn.qijianke.kkt.KKTApplication;

public class ScreenUtil {

    public static int[] getScreenSize() {
        WindowManager wm = (WindowManager) KKTApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics out = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(out);
        return new int[]{out.widthPixels, out.heightPixels};
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int identifier = KKTApplication.getInstance().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            result = KKTApplication.getInstance().getResources().getDimensionPixelSize(identifier);
        }
        return result;
    }
}
