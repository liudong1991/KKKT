package cn.qijianke.kkt.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.req.TotalDealAmountParam;
import cn.qijianke.kkt.model.resp.TotalDealAmountResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.ui.fragments.DealQueryGroupFragment;
import cn.qijianke.kkt.ui.fragments.DealQueryPersonalFragment;
import cn.qijianke.kkt.ui.view.BenefitTabView;
import cn.qijianke.kkt.utils.RSAUtil;

public class QueryDealActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.tab)
    BenefitTabView tab;
    @BindView(R.id.deal_total_tv)
    TextView deal_total_tv;

    Fragment[] fragments = {new DealQueryPersonalFragment(), new DealQueryGroupFragment()};

    double personalIncome;
    double teamIncome;

    @Override
    protected int getContentLayout() {
        return R.layout.query_deal_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("交易查询");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.list_container, fragments[0])
                .add(R.id.list_container, fragments[1])
                .show(fragments[0])
                .hide(fragments[1])
                .commit();
        tab.setOnTabChangedListener(onTabChangedListener);

        getTotalDealAmount();
    }

    private void getTotalDealAmount() {
        TotalDealAmountParam param = new TotalDealAmountParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setRsaValue(RSAUtil.getRSA(userId + token));
        showProgressDialog();
        RequestWrapper.getTotalDealAmount(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveTotalDealAmountResult(TotalDealAmountResp totalDealAmountResp) {
        if (totalDealAmountResp.getAmount() != null) {
            deal_total_tv.setText(String.format("%1$.2f元", totalDealAmountResp.getAmount() / 100d));
            personalIncome = totalDealAmountResp.getPersonalIncome() == null ? 0 : totalDealAmountResp.getPersonalIncome();
            teamIncome = totalDealAmountResp.getTeamIncome() == null ? 0 : totalDealAmountResp.getTeamIncome();
        }

    }

    private BenefitTabView.OnTabChangedListener onTabChangedListener = new BenefitTabView.OnTabChangedListener() {
        @Override
        public void onChange(View view) {
            Object tag = view.getTag();
            if (tag instanceof Integer) {
                switch ((Integer) tag) {
                    case 0:
                        showTab(0);
                        deal_total_tv.setText(String.format("%1$.2f元", personalIncome / 100d));
                        break;
                    case 1:
                        showTab(1);
                        deal_total_tv.setText(String.format("%1$.2f元", teamIncome / 100d));
                        break;
                }
            }
        }
    };

    private void showTab(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragments) {
            transaction.hide(fragment);
        }
        transaction.show(fragments[index]).commit();
    }
}
