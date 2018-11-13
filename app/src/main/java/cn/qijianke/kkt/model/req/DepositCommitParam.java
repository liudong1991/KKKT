package cn.qijianke.kkt.model.req;

import cn.qijianke.kkt.model.BaseModel;

public class DepositCommitParam extends BaseModel {
    private String userId;
    private String token;
    private String amount;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRsaValue() {
        return rsaValue;
    }

    public void setRsaValue(String rsaValue) {
        this.rsaValue = rsaValue;
    }
}
