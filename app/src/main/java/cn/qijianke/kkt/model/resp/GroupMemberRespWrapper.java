package cn.qijianke.kkt.model.resp;

import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.model.BaseModel;

public class GroupMemberRespWrapper extends BaseModel {

    private List<GroupMemberResp> list = new ArrayList<>();

    public List<GroupMemberResp> getList() {
        return list;
    }

    public void setList(List<GroupMemberResp> list) {
        this.list = list;
    }
}
