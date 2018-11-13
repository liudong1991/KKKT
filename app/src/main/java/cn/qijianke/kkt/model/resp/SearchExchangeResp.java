package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class SearchExchangeResp extends BaseModel {

    private String id;
    private String exchangeTicket;
    private String points;
    private Double amount;
    private String status;
    private String remark;
    private String opinion;
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExchangeTicket() {
        return exchangeTicket;
    }

    public void setExchangeTicket(String exchangeTicket) {
        this.exchangeTicket = exchangeTicket;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
