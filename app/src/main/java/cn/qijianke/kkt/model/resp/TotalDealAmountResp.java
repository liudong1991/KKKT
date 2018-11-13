package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class TotalDealAmountResp extends BaseModel {

    private Double amount;
    private Double personalIncome;
    private Double teamIncome;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPersonalIncome() {
        return personalIncome;
    }

    public void setPersonalIncome(Double personalIncome) {
        this.personalIncome = personalIncome;
    }

    public Double getTeamIncome() {
        return teamIncome;
    }

    public void setTeamIncome(Double teamIncome) {
        this.teamIncome = teamIncome;
    }
}
