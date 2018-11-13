package cn.qijianke.kkt.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by e311 on 2016/5/11.
 */
public class VersionUtil {


    public static String getSysVersion() {
        String rs = android.os.Build.VERSION.SDK + "###" + android.os.Build.VERSION.RELEASE;
        return rs;
    }

    public static String getSysModel() {
        String rs = android.os.Build.MODEL;
        return rs;
    }

    public static String getVersion(Context context) {
        String versionName = "1.0.0";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = "1.0.0";
            e.printStackTrace();
        }
        return versionName;
    }

}
