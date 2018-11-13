package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class VipInfoResp extends BaseModel {
    private String vip;
    private String svip;

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getSvip() {
        return svip;
    }

    public void setSvip(String svip) {
        this.svip = svip;
    }
}
