package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class DepositRecordResp extends BaseModel {
    private String createdAt;
    private Double amount;
    private String remark;
    private String status;

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
        this.amount = amount == null ? 0L : amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
