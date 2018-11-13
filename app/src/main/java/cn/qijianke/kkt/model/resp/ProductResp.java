package cn.qijianke.kkt.model.resp;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

import cn.qijianke.kkt.model.BaseModel;

@SmartTable
public class ProductResp extends BaseModel {

    @SmartColumn(id = 1, name = "积分数")
    private String points;
    @SmartColumn(id = 2, name = "兑换商品")
    private String product;
    @SmartColumn(id = 3, name = "兑换次数")
    private String limits;
    @SmartColumn(id = 4, name = "回购价")
    private String buyBackPrice1;

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

    public String getLimits() {
        return limits;
    }

    public void setLimits(String limits) {
        this.limits = limits;
    }

    public String getBuyBackPrice1() {
        return buyBackPrice1;
    }

    public void setBuyBackPrice1(String buyBackPrice1) {
        this.buyBackPrice1 = buyBackPrice1;
    }
}
