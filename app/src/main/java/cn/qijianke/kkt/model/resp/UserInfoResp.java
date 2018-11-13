package cn.qijianke.kkt.model.resp;

import android.os.Parcel;
import android.os.Parcelable;

import cn.qijianke.kkt.model.BaseModel;

public class UserInfoResp extends BaseModel implements Parcelable {

    private String from;

    private String id;
    private String name;
    private String idCardNum;
    private String bankCardNum;
    private String bankCardIssue;
    private String mobileNum;
    private String receiveAddress;
    private String email;
    private String wxPayId;
    private String alipayId;
    private String leaderName;
    private String leaderMobileNum;
    private String level;
    private String posId;

    public UserInfoResp() {
    }

    public UserInfoResp(String from) {
        this.from = from;
    }

    protected UserInfoResp(Parcel in) {
        from = in.readString();
        id = in.readString();
        name = in.readString();
        idCardNum = in.readString();
        bankCardNum = in.readString();
        bankCardIssue = in.readString();
        mobileNum = in.readString();
        receiveAddress = in.readString();
        email = in.readString();
        wxPayId = in.readString();
        alipayId = in.readString();
        leaderName = in.readString();
        leaderMobileNum = in.readString();
        level = in.readString();
        posId = in.readString();
    }

    public static final Creator<UserInfoResp> CREATOR = new Creator<UserInfoResp>() {
        @Override
        public UserInfoResp createFromParcel(Parcel in) {
            return new UserInfoResp(in);
        }

        @Override
        public UserInfoResp[] newArray(int size) {
            return new UserInfoResp[size];
        }
    };

    public String getFrom() {
        return from;
    }

    public UserInfoResp setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getBankCardNum() {
        return bankCardNum;
    }

    public void setBankCardNum(String bankCardNum) {
        this.bankCardNum = bankCardNum;
    }

    public String getBankCardIssue() {
        return bankCardIssue;
    }

    public void setBankCardIssue(String bankCardIssue) {
        this.bankCardIssue = bankCardIssue;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWxPayId() {
        return wxPayId;
    }

    public void setWxPayId(String wxPayId) {
        this.wxPayId = wxPayId;
    }

    public String getAlipayId() {
        return alipayId;
    }

    public void setAlipayId(String alipayId) {
        this.alipayId = alipayId;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getLeaderMobileNum() {
        return leaderMobileNum;
    }

    public void setLeaderMobileNum(String leaderMobileNum) {
        this.leaderMobileNum = leaderMobileNum;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(from);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(idCardNum);
        parcel.writeString(bankCardNum);
        parcel.writeString(bankCardIssue);
        parcel.writeString(mobileNum);
        parcel.writeString(receiveAddress);
        parcel.writeString(email);
        parcel.writeString(wxPayId);
        parcel.writeString(alipayId);
        parcel.writeString(leaderName);
        parcel.writeString(leaderMobileNum);
        parcel.writeString(level);
        parcel.writeString(posId);
    }
}
