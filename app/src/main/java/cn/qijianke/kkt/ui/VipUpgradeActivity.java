package cn.qijianke.kkt.ui;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.event.PaySuccessEvent;
import cn.qijianke.kkt.model.req.VipInfoParam;
import cn.qijianke.kkt.model.req.VipUpgradeParam;
import cn.qijianke.kkt.model.resp.VipInfoResp;
import cn.qijianke.kkt.model.resp.VipUpgradeResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.ui.view.VipTabView;
import cn.qijianke.kkt.utils.AlipayUtil;
import cn.qijianke.kkt.utils.ImageUtil;
import cn.qijianke.kkt.utils.RSAUtil;

public class VipUpgradeActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.ad_vip_upgrade)
    ImageView ad_vip_upgrade;
    @BindView(R.id.vip)
    VipTabView vip;
    @BindView(R.id.gold_vip)
    VipTabView gold_vip;

    @Override
    protected int getContentLayout() {
        return R.layout.vip_upgrade_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("会员升级");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();
        ImageUtil.adjustImg(ad_vip_upgrade, R.mipmap.ad_vip_upgrade);

        getVipInfo();
    }

    private void getVipInfo() {
        VipInfoParam param = new VipInfoParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setRsaValue(RSAUtil.getRSA(userId + token));
        showProgressDialog();
        RequestWrapper.getVipInfo(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveVipInfoResult(VipInfoResp vipInfoResp) {
        vip.setVip_price(vipInfoResp.getVip() + "元");
        gold_vip.setVip_price(vipInfoResp.getSvip() + "元");
    }

    @OnClick(value = {R.id.goto_pay_btn})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.goto_pay_btn:
                String category = 1 == VipTabView.type ? "HIGH" : "GOLD";
                String level = Session.getSession().getLoginResp().getLevel();
                if ("HIGH".equals(level) && "HIGH".equals(category)) {
                    showToast("您已经是高级会员");
                    return;
                }
                if ("GOLD".equals(level)) {
                    showToast("您已经是黄金会员");
                    return;
                }
                VipUpgradeParam param = new VipUpgradeParam();
                String userId = Session.getSession().getLoginResp().getUserId();
                String token = Session.getSession().getLoginResp().getToken();
                param.setUserId(userId);
                param.setToken(token);
                param.setCategory(category);
                param.setRsaValue(RSAUtil.getRSA(userId + token));
                showProgressDialog();
                RequestWrapper.vipUpgrade(param);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveVipUpgradeOrderResult(VipUpgradeResp vipUpgradeResp) {
        final String orderInfo = vipUpgradeResp.getPayOrderInfo();
        Log.i("spideriot.alipay", orderInfo);
        AlipayUtil.pay(VipUpgradeActivity.this, orderInfo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receivePayResult(PaySuccessEvent event) {
        Session.getSession().getLoginResp().setLevel(VipTabView.type == 1 ? "HIGH" : "GOLD");
        Session.getSession().saveLoginResp2Disk();
    }
}
