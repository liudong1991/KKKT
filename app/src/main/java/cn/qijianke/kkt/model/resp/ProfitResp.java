package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class ProfitResp extends BaseModel {

    private String tradeTime;
    private Double profitAmt;
    private String recommendTime;
    private String recommendMobileNum;

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public Double getProfitAmt() {
        return profitAmt;
    }

    public void setProfitAmt(Double profitAmt) {
        this.profitAmt = profitAmt == null ? 0L : profitAmt;
    }

    public String getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(String recommendTime) {
        this.recommendTime = recommendTime;
    }

    public String getRecommendMobileNum() {
        return recommendMobileNum;
    }

    public void setRecommendMobileNum(String recommendMobileNum) {
        this.recommendMobileNum = recommendMobileNum;
    }
}
