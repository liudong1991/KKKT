package cn.qijianke.kkt.model.req;

import cn.qijianke.kkt.model.BaseModel;

public class UserInfoParam extends BaseModel {

    private String from;

    private String userId;
    private String token;
    private String rsaValue;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRsaValue() {
        return rsaValue;
    }

    public void setRsaValue(String rsaValue) {
        this.rsaValue = rsaValue;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
