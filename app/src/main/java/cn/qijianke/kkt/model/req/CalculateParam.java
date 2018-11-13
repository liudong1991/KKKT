package cn.qijianke.kkt.model.req;

import cn.qijianke.kkt.model.BaseModel;

public class CalculateParam extends BaseModel {
    private String userId;
    private String token;
    private String bankCode;
    private String points;
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

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getRsaValue() {
        return rsaValue;
    }

    public void setRsaValue(String rsaValue) {
        this.rsaValue = rsaValue;
    }
}
