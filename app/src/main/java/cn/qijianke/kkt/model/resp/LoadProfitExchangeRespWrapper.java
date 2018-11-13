package cn.qijianke.kkt.model.resp;

import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.model.BaseModel;

public class LoadProfitExchangeRespWrapper extends BaseModel {

    private List<LoadProfitExchangeResp> list = new ArrayList<>();

    public List<LoadProfitExchangeResp> getList() {
        return list;
    }

    public void setList(List<LoadProfitExchangeResp> list) {
        this.list = list;
    }
}
