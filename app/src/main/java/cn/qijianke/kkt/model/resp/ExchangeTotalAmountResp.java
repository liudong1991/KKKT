package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class ExchangeTotalAmountResp extends BaseModel {

    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {

        this.amount = amount == null ? 0 : amount;
    }
}
