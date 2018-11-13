package cn.qijianke.kkt.model.req;

import cn.qijianke.kkt.model.BaseModel;

public class RegisterParam extends BaseModel {

    private String userPhone;
    private String userPwd;
    private String verificationCode;
    private String rsaValue;

    private String refereeNo;

    public String getRefereeNo() {
        return refereeNo;
    }

    public void setRefereeNo(String refereeNo) {
        this.refereeNo = refereeNo;
    }

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

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getRsaValue() {
        return rsaValue;
    }

    public void setRsaValue(String rsaValue) {
        this.rsaValue = rsaValue;
    }
}
