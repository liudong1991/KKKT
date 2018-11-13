package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class ParterResp extends BaseModel {

    private String userName;
    private String mobileNum;
    private String customerId;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
