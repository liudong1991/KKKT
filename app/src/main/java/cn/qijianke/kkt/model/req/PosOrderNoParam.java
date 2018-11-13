package cn.qijianke.kkt.model.req;

import cn.qijianke.kkt.model.BaseModel;

public class PosOrderNoParam extends BaseModel {

    private String userId;
    private String token;
    private String posId;
    private String quantity;
    private String posCategory;
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

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPosCategory() {
        return posCategory;
    }

    public void setPosCategory(String posCategory) {
        this.posCategory = posCategory;
    }

    public String getRsaValue() {
        return rsaValue;
    }

    public void setRsaValue(String rsaValue) {
        this.rsaValue = rsaValue;
    }
}
