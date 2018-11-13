package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class LoadProfitExchangeResp extends BaseModel {
    private String createdAt;
    private Double amount;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount == null ? 0 : amount;
    }
}
