package cn.qijianke.kkt.model.resp;

import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.model.BaseModel;

public class BenefitDetailRespWrapper extends BaseModel {

    private List<BenefitDetailResp> list = new ArrayList<>();

    {
        for (int i = 0; i < 10; i++) {
            list.add(new BenefitDetailResp());
        }
    }

    public List<BenefitDetailResp> getList() {
        return list;
    }

    public void setList(List<BenefitDetailResp> list) {
        this.list = list;
    }
}
