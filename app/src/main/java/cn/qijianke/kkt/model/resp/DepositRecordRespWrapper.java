package cn.qijianke.kkt.model.resp;

import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.model.BaseModel;

public class DepositRecordRespWrapper extends BaseModel {

    private List<DepositRecordResp> list = new ArrayList<>();

    public List<DepositRecordResp> getList() {
        return list;
    }

    public void setList(List<DepositRecordResp> list) {
        this.list = list;
    }
}
