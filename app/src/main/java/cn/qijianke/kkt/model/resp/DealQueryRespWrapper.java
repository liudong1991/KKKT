package cn.qijianke.kkt.model.resp;

import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.model.BaseModel;

public class DealQueryRespWrapper extends BaseModel {

    private List<DealQueryResp> list = new ArrayList<>();

    public List<DealQueryResp> getList() {
        return list;
    }

    public void setList(List<DealQueryResp> list) {
        this.list = list;
    }
}
