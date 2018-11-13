package cn.qijianke.kkt.model.req;

import cn.qijianke.kkt.model.BaseModel;

public class LoginParam extends BaseModel {

    private String userPhone;
    private String userPwd;
    private String rsaValue;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getRsaValue() {
        return rsaValue;
    }

    public void setRsaValue(String rsaValue) {
        this.rsaValue = rsaValue;
    }
}
