package cn.qijianke.kkt.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.req.CalculateParam;
import cn.qijianke.kkt.model.resp.CalculateResp;
import cn.qijianke.kkt.model.resp.UserInfoResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.ui.handlers.UserInfoHandler;
import cn.qijianke.kkt.utils.ImageUtil;
import cn.qijianke.kkt.utils.RSAUtil;

public class PointsExchangeActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.exchange_img)
    ImageView exchange_img;
    @BindView(R.id.points_edit)
    EditText points_edit;
    @BindView(R.id.result_tv)
    EditText result_tv;
    @BindView(R.id.search_txt)
    TextView search_txt;

    String bankCode = "";
    String bankName = "";
    String memo = "";

    String points = "";

    @Override
    protected int getContentLayout() {
        return R.layout.points_exchange_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("积分兑换");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();
        this.bankCode = getIntent().getStringExtra("bankCode");
        this.bankName = getIntent().getStringExtra("bankName");
        this.memo = getIntent().getStringExtra("memo");
        ImageUtil.adjustImg(exchange_img, R.mipmap.exchange_bg, 10);
        points_edit.addTextChangedListener(new TextWatcher() {
            CharSequence old;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                old = charSequence;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (!Pattern.compile("[0-9]*").matcher(str).matches()) {
                    editable.delete(0, editable.length());
                    editable.append(old);
                }
                points = editable.toString();
            }
        });
        search_txt.setText(memo);
    }

    @OnClick(value = {R.id.exchange_btn, R.id.calculate_btn})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.exchange_btn:
                showProgressDialog();
                UserInfoHandler.refresh("pointsExchange");
                break;
            case R.id.calculate_btn:
                calculate();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveUserInfoResult(UserInfoResp userInfoResp) {
        if (!"pointsExchange".equals(userInfoResp.getFrom())) return;
        String level = userInfoResp.getLevel();
        if (!("HIGH".equals(level) || "GOLD".equals(level))) {
            showToast("您还是普通会员，不能参与兑换，请升级成为会员");
            startActivity(VipUpgradeActivity.class);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("bankCode", bankCode);
        startActivity(ExchangeDetailActivity.class, bundle);
    }

    private void calculate() {

        if (TextUtils.isEmpty(points) || Double.parseDouble(points) == 0) {
            showToast("请输入积分");
            return;
        }

        CalculateParam param = new CalculateParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setBankCode(bankCode);
        param.setPoints(points);
        param.setRsaValue(RSAUtil.getRSA(userId + token + bankCode + points));
        showProgressDialog();
        RequestWrapper.calculate(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveCalculateResult(CalculateResp calculateResp) {
        result_tv.setText(String.format("约%1$.2f元", calculateResp.getAmount() / 100d));
    }

}
