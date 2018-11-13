package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class DepositAmountResp extends BaseModel {

    private Double cash;
    private String comment;

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash == null ? 0L : cash;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
