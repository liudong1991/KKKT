package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class LoginResp extends BaseModel {
    private String token;
    private String userId;
    private String userName;
    private String userPhone;
    private String imageUrl;
    private String qqBindingSign;
    private String wechatBindingSign;
    private String level;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getQqBindingSign() {
        return qqBindingSign;
    }

    public void setQqBindingSign(String qqBindingSign) {
        this.qqBindingSign = qqBindingSign;
    }

    public String getWechatBindingSign() {
        return wechatBindingSign;
    }

    public void setWechatBindingSign(String wechatBindingSign) {
        this.wechatBindingSign = wechatBindingSign;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
