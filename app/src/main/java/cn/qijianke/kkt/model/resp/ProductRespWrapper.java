package cn.qijianke.kkt.model.resp;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.model.BaseModel;

public class ProductRespWrapper extends BaseModel {

    private List<ProductResp> list = new ArrayList<>();

    public ProductRespWrapper() {
    }

    public ProductRespWrapper(List<ProductResp> list) {
        if (list != null)
            this.list = list;
    }

    public List<ProductResp> getList() {
        return list;
    }

    public void setList(List<ProductResp> list) {
        this.list = list;
    }


    public void convert() {
        if (list != null)
            for (int i = 0; i < list.size(); i++) {
                String buyBackPrice1 = list.get(i).getBuyBackPrice1();
                if (!TextUtils.isEmpty(buyBackPrice1)) {
                    list.get(i).setBuyBackPrice1(String.format("%1$.2f", Double.parseDouble(buyBackPrice1) / 100));
                }
            }
    }
}
