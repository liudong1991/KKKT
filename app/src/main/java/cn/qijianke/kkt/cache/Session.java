package cn.qijianke.kkt.cache;

import android.text.TextUtils;

import com.google.gson.Gson;

import cn.qijianke.kkt.model.resp.LoginResp;
import cn.qijianke.kkt.utils.SPUtil;

public class Session {

    private static final String USER_KEY = "USER_KEY";

    private static Session session;

    private LoginResp loginResp;

    private Session() {

    }

    public static Session getSession() {
        if (session == null) {
            synchronized (Session.class) {
                if (session == null) {
                    session = new Session();
                }
            }
        }
        return session;
    }

    public LoginResp getLoginResp() {
        if (loginResp == null) {
            String userId = SPUtil.get(USER_KEY);
            if (!TextUtils.isEmpty(userId)) {
                loginResp = getLoginRespFromDisk(userId);
            }
        }
        return loginResp == null ? new LoginResp() : loginResp;
    }

    public void setLoginResp(LoginResp loginResp) {
        this.loginResp = loginResp;
        saveLoginResp2Disk(loginResp);
    }

    private LoginResp getLoginRespFromDisk(String userId) {
        if (!TextUtils.isEmpty(userId)) {
            String json = SPUtil.get(userId);
            if (!TextUtils.isEmpty(json)) {
                return new Gson().fromJson(json, LoginResp.class);
            }
        }
        return null;
    }

    private void saveLoginResp2Disk(LoginResp loginResp) {
        if (loginResp != null && !TextUtils.isEmpty(loginResp.getUserId())) {
            String json = new Gson().toJson(loginResp);
            SPUtil.put(loginResp.getUserId(), json);
        }
    }

    public void saveLoginResp2Disk() {
        saveLoginResp2Disk(loginResp);
    }

    public void login(LoginResp loginResp) {
        if (loginResp != null && !TextUtils.isEmpty(loginResp.getUserId())) {
            SPUtil.put(USER_KEY, loginResp.getUserId());
            setLoginResp(loginResp);
        }
    }

    public void logout() {
        SPUtil.put(USER_KEY, "");
        loginResp = null;
    }

    public boolean isLogin() {
        return getLoginResp().getUserId() != null;
    }
}
