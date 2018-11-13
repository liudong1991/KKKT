package cn.qijianke.kkt.model.req;

import cn.qijianke.kkt.model.BaseModel;

public class SmsParam extends BaseModel {

    private String userPhone;
    private String rSign;
    private String captchaText;
    private String rsaValue;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getrSign() {
        return rSign;
    }

    public void setrSign(String rSign) {
        this.rSign = rSign;
    }

    public String getCaptchaText() {
        return captchaText;
    }

    public void setCaptchaText(String captchaText) {
        this.captchaText = captchaText;
    }

    public String getRsaValue() {
        return rsaValue;
    }

    public void setRsaValue(String rsaValue) {
        this.rsaValue = rsaValue;
    }
}
