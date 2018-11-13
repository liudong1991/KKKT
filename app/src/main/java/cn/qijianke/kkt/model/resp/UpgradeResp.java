package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class UpgradeResp extends BaseModel {


    /**
     * isUpdate : O
     * remark : 发现新版本
     * name : null
     * version : 1.1.6
     * whetherUpdate : yes
     */

    private String isUpdate;
    private String remark;
    private String name;
    private String version;
    private String whetherUpdate;
    private String androidUrl;
    private String target;

    public UpgradeResp() {
    }

    public UpgradeResp(String target) {
        this.target = target;
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getWhetherUpdate() {
        return whetherUpdate;
    }

    public void setWhetherUpdate(String whetherUpdate) {
        this.whetherUpdate = whetherUpdate;
    }

    public String getAndroidUrl() {
        return androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public String getTarget() {
        return target;
    }

    public UpgradeResp setTarget(String target) {
        this.target = target;
        return this;
    }
}
