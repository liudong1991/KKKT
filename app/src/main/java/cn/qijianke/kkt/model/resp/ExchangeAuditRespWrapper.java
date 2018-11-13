package cn.qijianke.kkt.model.resp;

import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.model.BaseModel;

public class ExchangeAuditRespWrapper extends BaseModel {

    private List<ExchangeAuditResp> list = new ArrayList<>();

    public List<ExchangeAuditResp> getList() {
        return list;
    }

    public void setList(List<ExchangeAuditResp> list) {
        this.list = list;
    }
}
