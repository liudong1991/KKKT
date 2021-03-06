package cn.qijianke.kkt.model.req;

import cn.qijianke.kkt.model.BaseModel;

public class ProfitParam extends BaseModel {

    private String userId;
    private String token;
    private int pageId;
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

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
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
}
