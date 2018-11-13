package cn.qijianke.kkt.model.resp;

import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.model.BaseModel;

public class DealDetailRespWrapper extends BaseModel {

    private List<DealDetailResp> list = new ArrayList<>();

    public List<DealDetailResp> getList() {
        return list;
    }

    public void setList(List<DealDetailResp> list) {
        this.list = list;
    }
}
