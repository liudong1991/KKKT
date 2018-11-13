package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class DealQueryResp extends BaseModel {

    private String tradeTime;
    private String tradeCount;
    private String tradeAmt;

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(String tradeCount) {
        this.tradeCount = tradeCount;
    }

    public String getTradeAmt() {
        return tradeAmt;
    }

    public void setTradeAmt(String tradeAmt) {
        this.tradeAmt = tradeAmt;
    }
}
