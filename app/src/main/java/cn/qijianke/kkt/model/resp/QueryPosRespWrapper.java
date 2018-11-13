package cn.qijianke.kkt.model.resp;

import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.model.BaseModel;

public class QueryPosRespWrapper extends BaseModel {

    private List<QueryPosResp> list = new ArrayList<>();

    public List<QueryPosResp> getList() {
        return list;
    }

    public void setList(List<QueryPosResp> list) {
        this.list = list;
    }
}
