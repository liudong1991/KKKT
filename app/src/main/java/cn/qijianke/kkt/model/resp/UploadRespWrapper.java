package cn.qijianke.kkt.model.resp;

import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.model.BaseModel;

public class UploadRespWrapper extends BaseModel {

    private List<String> list = new ArrayList<>();

    public UploadRespWrapper() {
    }

    public UploadRespWrapper(List<String> list) {
        if (list != null)
            this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
