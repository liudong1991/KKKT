package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class ProductTypeResp extends BaseModel {

    private String id;
    private String points;
    private String product;
    private String bankCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
