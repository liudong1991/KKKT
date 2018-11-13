package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class ProfitTotalResp extends BaseModel {

    private Double totalIncome;
    private Double personalIncome;
    private Double personalTeam;
    private Double ExtensionIncome;

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome == null ? 0L : totalIncome;
    }

    public Double getPersonalIncome() {
        return personalIncome;
    }

    public void setPersonalIncome(Double personalIncome) {
        this.personalIncome = personalIncome == null ? 0L : personalIncome;
    }

    public Double getPersonalTeam() {
        return personalTeam;
    }

    public void setPersonalTeam(Double personalTeam) {
        this.personalTeam = personalTeam == null ? 0L : personalTeam;
    }

    public Double getExtensionIncome() {
        return ExtensionIncome;
    }

    public void setExtensionIncome(Double extensionIncome) {
        this.ExtensionIncome = extensionIncome == null ? 0L : extensionIncome;
    }
}
