package cn.qijianke.kkt.ui;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.req.ProfitTotalParam;
import cn.qijianke.kkt.model.resp.ProfitTotalResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.ui.fragments.BenefitExchangeFragment;
import cn.qijianke.kkt.ui.fragments.BenefitGroupFragment;
import cn.qijianke.kkt.ui.fragments.BenefitPersonalFragment;
import cn.qijianke.kkt.ui.fragments.BenefitSpreadFragment;
import cn.qijianke.kkt.ui.view.BenefitTabView;
import cn.qijianke.kkt.utils.RSAUtil;

public class BenefitDetailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.tab_moudle)
    BenefitTabView tab_moudle;
    @BindView(R.id.total_money_v)
    TextView total_money_v;
    @BindView(R.id.personal_profit_tv)
    TextView personal_profit_tv;
    @BindView(R.id.group_profit_tv)
    TextView group_profit_tv;
    @BindView(R.id.spread_profit_tv)
    TextView spread_profit_tv;

    private Fragment[] fragments = {new BenefitPersonalFragment(), new BenefitGroupFragment(), new BenefitSpreadFragment(), new BenefitExchangeFragment()};

    @Override
    protected int getContentLayout() {
        return R.layout.benefit_detail_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("收益明细");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();
        tab_moudle.setOnTabChangedListener(onTabChangedListener);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_container, fragments[0])
                .add(R.id.detail_container, fragments[1])
                .add(R.id.detail_container, fragments[2])
                .add(R.id.detail_container, fragments[3])
                .show(fragments[0])
                .hide(fragments[1])
                .hide(fragments[2])
                .hide(fragments[3])
                .commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfitTotal();
    }

    private void getProfitTotal() {
        ProfitTotalParam param = new ProfitTotalParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setRsaValue(RSAUtil.getRSA(userId + token));
        showProgressDialog();
        RequestWrapper.getProfitTotal(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveProfitTotalResult(ProfitTotalResp profitTotalResp) {
        total_money_v.setText(String.format("%1$.2f元", profitTotalResp.getTotalIncome() / 100d));
        personal_profit_tv.setText(String.format("%1$.2f元", profitTotalResp.getPersonalIncome() / 100d));
        group_profit_tv.setText(String.format("%1$.2f元", profitTotalResp.getPersonalTeam() / 100d));
        spread_profit_tv.setText(String.format("%1$.2f元", profitTotalResp.getExtensionIncome() / 100d));
    }

    private BenefitTabView.OnTabChangedListener onTabChangedListener = new BenefitTabView.OnTabChangedListener() {
        @Override
        public void onChange(View view) {
            Object tag = view.getTag();
            if (tag instanceof Integer) {
                switch ((Integer) tag) {
                    case 0:
                        showTab(0);
                        break;
                    case 1:
                        showTab(1);
                        break;
                    case 2:
                        showTab(2);
                        break;
                    case 3:
                        showTab(3);
                        break;
                }
            }
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
    }
}
