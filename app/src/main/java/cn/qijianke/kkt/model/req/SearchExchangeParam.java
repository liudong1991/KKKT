package cn.qijianke.kkt.model.req;

import cn.qijianke.kkt.model.BaseModel;

public class SearchExchangeParam extends BaseModel {

    private String tag;

    private String userId;
    private String token;
    private String mobileNum;
    private String pos;
    private int pageId;
    private String rsaValue;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

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
}
