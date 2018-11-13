package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class PosInfoResp extends BaseModel {

    private String id;
    private String posCategory;
    private String unitPrice;
    private String posType = "K100T6Q8888";
    private String localPosType = "K100T6Q8888";

    private boolean isChecked;
    private int num;

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosCategory() {
        return posCategory;
    }

    public void setPosCategory(String posCategory) {
        this.posCategory = posCategory;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getLocalPosType() {
        return localPosType;
    }

    public void setLocalPosType(String localPosType) {
        this.localPosType = localPosType;
    }
}
