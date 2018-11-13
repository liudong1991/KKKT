package cn.qijianke.kkt.model.resp;

import java.util.ArrayList;
import java.util.List;

import cn.qijianke.kkt.model.BaseModel;

public class SearchExchangeRespWrapper extends BaseModel {

    private String tag;

    private List<SearchExchangeResp> list = new ArrayList<>();

    public SearchExchangeRespWrapper() {
    }

    public SearchExchangeRespWrapper(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public SearchExchangeRespWrapper setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public List<SearchExchangeResp> getList() {
        return list;
    }

    public void setList(List<SearchExchangeResp> list) {
        this.list = list;
    }
}
