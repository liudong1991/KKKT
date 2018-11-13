package cn.qijianke.kkt.common;

import cn.qijianke.kkt.BuildConfig;

public class Constants {

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int STATUS_BAR_HEIGHT;
    public static String DEVICE_ID;
    public static String CLIENT_ID = "1"; //android

    //public static String HOST = "http://ble2.spider-iot.com";
    private static String DEBUG_HOST = "http://kktdev.spider-iot.com";
    private static String RELEASE_HOST = "http://kka.spider-iot.com";
    public static String HOST = BuildConfig.DEBUG ? DEBUG_HOST : RELEASE_HOST;

//    public static String HOST = "http://kka.spider-iot.com";
    //public static String HOST = "http://19n44249q6.iask.in:45986";

    public static String OPENAPI_SECURITY_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFYyhak5slMxWMrlBO/Eau5P7EvxdBwXrQPdVBKO2w7MiZ3b98iRI3oy2ELqorc0UFIoUyH3tirzcc0191m6Zt61/OLcUWUnzxOGfaNtG9MmPOf15EU7K3fMD4LTKrlWaQzns7QZmdFz/jkRGBvZ3HNoPtuPUT7sb1gCjUJaeefwIDAQAB";


    //短信标识 REGISTER、RESET、CHECKPHONE
    public static final String REGISTER = "REGISTER"; //注册
    public static final String RESET = "RESET"; //重置
    public static final String CHECKPHONE = "CHECKPHONE"; //手机

    public static final String EVENT_KEY = "event";
    public static final String LOGIN_EXPIRE_EVENT = "login_expire";

}
