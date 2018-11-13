package cn.qijianke.kkt.model.resp;

import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.model.BaseModel;

public class PosInfoRespWrapper extends BaseModel {

    private List<PosInfoResp> list = new ArrayList<>();

    public List<PosInfoResp> getList() {
        return list;
    }

    public void setList(List<PosInfoResp> list) {
        this.list = list;
    }
}
