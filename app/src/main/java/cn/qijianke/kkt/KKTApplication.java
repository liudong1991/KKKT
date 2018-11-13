package cn.qijianke.kkt;

import android.app.Application;

import cn.qijianke.kkt.common.Constants;
import cn.qijianke.kkt.utils.DeviceUtil;
import cn.qijianke.kkt.utils.ScreenUtil;

public class KKTApplication extends Application {

    private static KKTApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        init();
    }

    private void init() {
        initScreenSize();
    }

    private void initScreenSize() {
        int[] screenSize = ScreenUtil.getScreenSize();
        Constants.SCREEN_WIDTH = screenSize[0];
        Constants.SCREEN_HEIGHT = screenSize[1];
        Constants.STATUS_BAR_HEIGHT = ScreenUtil.getStatusBarHeight();
        Constants.DEVICE_ID = DeviceUtil.getDeViceId(this);
    }


    public static KKTApplication getInstance() {
        return instance;
    }
}
