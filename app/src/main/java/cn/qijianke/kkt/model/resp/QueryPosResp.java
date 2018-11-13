package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class QueryPosResp extends BaseModel {

    private String phySn;
    private String userName;
    private String mobileNum;
    private String activatedAt;

    public String getPhySn() {
        return phySn;
    }

    public void setPhySn(String phySn) {
        this.phySn = phySn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(String activatedAt) {
        this.activatedAt = activatedAt;
    }
}
