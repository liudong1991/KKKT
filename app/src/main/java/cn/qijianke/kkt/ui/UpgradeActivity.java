package cn.qijianke.kkt.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.KKTApplication;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.req.UpgradeParam;
import cn.qijianke.kkt.model.resp.UpgradeResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.ui.handlers.UpdateHandler;
import cn.qijianke.kkt.utils.RSAUtil;
import cn.qijianke.kkt.utils.VersionUtil;

public class UpgradeActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.name)
    TextView name;

    @Override
    protected int getContentLayout() {
        return R.layout.upgrade_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("版本升级");
        sub_title.setVisibility(View.GONE);
        name.setText("版本号：v" + VersionUtil.getVersion(KKTApplication.getInstance()));
    }

    @Override
    protected void init() {
        super.init();
    }

    @OnClick(value = {R.id.upgrade_btn})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.upgrade_btn:
                upgrade();
                break;
        }
    }

    private void upgrade() {
        UpgradeParam param = new UpgradeParam();
        param.setTarget("upgrade");
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setRsaValue(RSAUtil.getRSA(userId + token));
        showProgressDialog();
        RequestWrapper.upgrade(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void upgradeResult(UpgradeResp upgradeResp) {
        if (!"upgrade".equals(upgradeResp.getTarget())) return;
        if ("yes".equals(upgradeResp.getWhetherUpdate())) {
            UpdateHandler.update(this, upgradeResp);
        } else {
            showToast(upgradeResp.getRemark());
        }
    }

    public void goToMarket() {
        Uri uri = Uri.parse("market://details?id=cn.qijianke.kkt");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            showToast("");
        }
    }
}
