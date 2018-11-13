package cn.qijianke.kkt.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.style.FontStyle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.req.ProductParam;
import cn.qijianke.kkt.model.resp.ProductResp;
import cn.qijianke.kkt.model.resp.ProductRespWrapper;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.utils.RSAUtil;

public class ExchangeDetailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.table)
    SmartTable<ProductResp> table;

    String bankCode = "";

    List<ProductResp> list = new ArrayList<>();

    {
        ProductResp resp = new ProductResp();
        resp.setPoints(" ");
        resp.setProduct(" ");
        resp.setLimits(" ");
        resp.setBuyBackPrice1(" ");

        list.add(resp);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.exchange_detail_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("详情");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();
        bankCode = getIntent().getStringExtra("bankCode");
        FontStyle fontStyle = new FontStyle();
        fontStyle.setTextColor(Color.parseColor("#5096E4"));
        table.getConfig().setColumnTitleStyle(fontStyle);
        table.getConfig().setShowXSequence(false);
        table.getConfig().setShowTableTitle(false);
        table.getConfig().setShowYSequence(false);
        table.setData(list);
        getProducts();
    }

    @OnClick(value = {R.id.baodan_btn, R.id.contact_service})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.baodan_btn:
                Bundle bundle = new Bundle();
                bundle.putString("bankCode", bankCode);
                startActivity(FillInBaodanActivity.class, bundle);
                break;
            case R.id.contact_service:
                startActivity(ContactCustomerServiceActivity.class);
                break;
        }
    }

    private void getProducts() {
        ProductParam param = new ProductParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setBankCode(bankCode);
        param.setRsaValue(RSAUtil.getRSA(userId + token + bankCode));
        showProgressDialog();
        RequestWrapper.getProducts(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveProductsResult(ProductRespWrapper productRespWrapper) {
        productRespWrapper.convert();
        List<ProductResp> result = productRespWrapper.getList();
        if (result != null) {
            list.clear();
            list.addAll(result);
            table.setData(list);
        }
    }
}
