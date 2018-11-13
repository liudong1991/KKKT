package cn.qijianke.kkt.model.resp;

import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.model.BaseModel;

public class PersonalProfitWrapper extends BaseModel {

    private List<ProfitResp> list = new ArrayList<>();

    public List<ProfitResp> getList() {
        return list;
    }

    public void setList(List<ProfitResp> list) {
        this.list = list;
    }
}
