package cn.qijianke.kkt.model.resp;

import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.model.BaseModel;

public class ProductTypeRespWrapper extends BaseModel {

    private List<ProductTypeResp> list = new ArrayList<>();

    public ProductTypeRespWrapper() {
    }

    public ProductTypeRespWrapper(List<ProductTypeResp> list) {
        if (list != null)
            this.list = list;
    }

    public List<ProductTypeResp> getList() {
        return list;
    }

    public void setList(List<ProductTypeResp> list) {
        this.list = list;
    }
}
