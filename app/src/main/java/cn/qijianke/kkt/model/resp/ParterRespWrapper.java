package cn.qijianke.kkt.model.resp;

import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.model.BaseModel;

public class ParterRespWrapper extends BaseModel {

    private List<ParterResp> list = new ArrayList<>();

    public List<ParterResp> getList() {
        return list;
    }

    public void setList(List<ParterResp> list) {
        this.list = list;
    }
}
