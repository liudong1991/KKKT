package cn.qijianke.kkt.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.event.SearchExchangeEvent;
import cn.qijianke.kkt.model.req.ExchangeTotalAmountParam;
import cn.qijianke.kkt.model.resp.ExchangeTotalAmountResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.ui.fragments.ExchangeAllFragment;
import cn.qijianke.kkt.ui.fragments.ExchangeAuditFailedFragment;
import cn.qijianke.kkt.ui.fragments.ExchangeAuditPassedFragment;
import cn.qijianke.kkt.ui.fragments.ExchangeAuditingFragment;
import cn.qijianke.kkt.ui.view.BenefitTabView;
import cn.qijianke.kkt.utils.RSAUtil;

public class QueryExchangeActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.tab)
    BenefitTabView tab;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.total_exchange_amount_tv)
    TextView total_exchange_amount_tv;

    String searchContent = "";

    Fragment[] fragments = {new ExchangeAllFragment(), new ExchangeAuditPassedFragment(), new ExchangeAuditingFragment(), new ExchangeAuditFailedFragment()};

    @Override
    protected int getContentLayout() {
        return R.layout.query_exchange_layout;
    }

    public String getSearchContent() {
        return searchContent;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("兑换查询");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment f : fragments) {
            transaction.add(R.id.list_container, f);
            transaction.hide(f);
        }
        transaction.show(fragments[0]).commit();
        tab.setOnTabChangedListener(onTabChangedListener);
        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchContent = editable.toString();
            }
        });

        getExchangeTotalAmount();
    }

    @OnClick(value = {R.id.search_btn})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.search_btn:
                search();
                break;
        }
    }

    private void search() {
        if (!TextUtils.isEmpty(searchContent)) {
            if (!Pattern.compile("[0-9]{4}-([0][1-9]|[1][0-2])").matcher(searchContent).matches()) {
                showToast("输入月份格式为yyyy-MM，如2018-07");
                return;
            }
        }

        EventBus.getDefault().post(new SearchExchangeEvent());
    }

    private void getExchangeTotalAmount() {
        ExchangeTotalAmountParam param = new ExchangeTotalAmountParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setRsaValue(RSAUtil.getRSA(userId + token));
        showProgressDialog();
        RequestWrapper.getExchangeTotalAmount(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveExchangeTotalAmountResult(ExchangeTotalAmountResp exchangeTotalAmountResp) {
        Double amount = exchangeTotalAmountResp.getAmount();
        total_exchange_amount_tv.setText(String.format("%1$.2f元", amount == null ? 0 : amount / 100));
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragments) {
            transaction.hide(fragment);
        }
        transaction.show(fragments[index]).commit();
    }
}
