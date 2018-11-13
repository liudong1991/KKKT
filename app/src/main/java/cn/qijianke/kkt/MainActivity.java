package cn.qijianke.kkt;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.common.Constants;
import cn.qijianke.kkt.model.req.UpgradeParam;
import cn.qijianke.kkt.model.resp.UpgradeResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.LoginActivity;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.ui.fragments.MainBenefitFragment;
import cn.qijianke.kkt.ui.fragments.MainExchangeFragment;
import cn.qijianke.kkt.ui.fragments.MainMineFragment;
import cn.qijianke.kkt.ui.fragments.MainSpreadFragment;
import cn.qijianke.kkt.ui.handlers.UpdateHandler;
import cn.qijianke.kkt.ui.view.CheckedImageView;
import cn.qijianke.kkt.utils.RSAUtil;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_benefit)
    CheckedImageView tab_benefit;
    @BindView(R.id.tab_spread)
    CheckedImageView tab_spread;
    @BindView(R.id.tab_exchange)
    CheckedImageView tab_exchange;
    @BindView(R.id.tab_mine)
    CheckedImageView tab_mine;

    private Fragment[] fragments = new Fragment[]{new MainBenefitFragment(), new MainSpreadFragment(), new MainExchangeFragment(), new MainMineFragment()};


    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void view() {
        super.view();
    }

    @Override
    protected void init() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_content, fragments[0])
                .add(R.id.main_content, fragments[1])
                .add(R.id.main_content, fragments[2])
                .add(R.id.main_content, fragments[3])
                .show(fragments[0])
                .hide(fragments[1])
                .hide(fragments[2])
                .hide(fragments[3])
                .commit();

        tab_benefit.setChecked(true);
        tab_benefit.setOnCheckedListener(onCheckedListener);
        tab_spread.setOnCheckedListener(onCheckedListener);
        tab_exchange.setOnCheckedListener(onCheckedListener);
        tab_mine.setOnCheckedListener(onCheckedListener);

        upgrade();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent != null) {
            if (Constants.LOGIN_EXPIRE_EVENT.equals(intent.getStringExtra(Constants.EVENT_KEY))) {
                localLogout();
            }
        }
    }

    private void localLogout() {
        if (Session.getSession().isLogin()) {
            showToast("你的登录已失效，请重新登录");
        }
        Session.getSession().logout();
        startActivity(LoginActivity.class);
        finish();
    }

    private CheckedImageView.OnCheckedListener onCheckedListener = new CheckedImageView.OnCheckedListener() {
        @Override
        public void check(View view) {
            switch (view.getId()) {
                case R.id.tab_benefit:
                    showTab(0);
                    break;
                case R.id.tab_spread:
                    showTab(1);
                    break;
                case R.id.tab_exchange:
                    showTab(2);
                    break;
                case R.id.tab_mine:
                    showTab(3);
                    break;
            }
            ((CheckedImageView) view).setChecked(true);
        }
    };

    private void showTab(int index) {
        getSupportFragmentManager().beginTransaction()
                .hide(fragments[0])
                .hide(fragments[1])
                .hide(fragments[2])
                .hide(fragments[3])
                .show(fragments[index])
                .commit();
        tab_benefit.setChecked(false);
        tab_spread.setChecked(false);
        tab_exchange.setChecked(false);
        tab_mine.setChecked(false);
    }

    private void upgrade() {
        UpgradeParam param = new UpgradeParam();
        param.setTarget("main");
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setRsaValue(RSAUtil.getRSA(userId + token));
        RequestWrapper.upgrade(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void upgradeResult(UpgradeResp upgradeResp) {
        if (!"main".equals(upgradeResp.getTarget())) return;
        if ("yes".equals(upgradeResp.getWhetherUpdate())) {
            UpdateHandler.update(this, upgradeResp);
        }
    }

}
