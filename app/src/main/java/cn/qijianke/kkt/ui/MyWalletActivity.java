package cn.qijianke.kkt.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.req.DepositAmountParam;
import cn.qijianke.kkt.model.req.DepositCommitParam;
import cn.qijianke.kkt.model.resp.DepositAmountResp;
import cn.qijianke.kkt.model.resp.DepositCommitResp;
import cn.qijianke.kkt.model.resp.UserInfoResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.ui.handlers.UserInfoHandler;
import cn.qijianke.kkt.utils.RSAUtil;

public class MyWalletActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.money_input)
    EditText money_input;
    @BindView(R.id.getable_money)
    TextView getable_money;
    @BindView(R.id.comment_tv)
    TextView comment_tv;

    String money = "0";
    String allMoney = "";

    @Override
    protected int getContentLayout() {
        return R.layout.my_wallet_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("我的钱包");
        sub_title.setText("提现记录");
    }

    @Override
    protected void init() {
        super.init();
        money_input.addTextChangedListener(new TextWatcher() {

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
                if (editable.length() == 0) {
                    money = "0";
                    return;
                }
                String s = editable.toString();
                if (!Pattern.compile("[1-9][0-9]*").matcher(s).matches()) {

                    Matcher matcher = Pattern.compile("[0]+([1-9][0-9]*)").matcher(s);
                    if (matcher.matches()) {
                        editable.delete(0, editable.length());
                        editable.append(matcher.group(1));
                        money = editable.toString();
                        return;
                    }
                    matcher = Pattern.compile("[0]+").matcher(s);
                    if (matcher.matches()) {
                        editable.delete(0, editable.length());
                        money = editable.toString();
                        return;
                    }
                    editable.delete(0, editable.length());
                    editable.append(old);
                }
                money = editable.toString();
            }
        });
        money_input.setSelection(money.length());

        showProgressDialog();
        getDepositAmount();
    }

    private void getDepositAmount() {
        DepositAmountParam param = new DepositAmountParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setRsaValue(RSAUtil.getRSA(userId + token));

        RequestWrapper.getDepositAmount(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveDepositAmountResult(DepositAmountResp depositAmountResp) {
        allMoney = depositAmountResp.getCash() + "";
        getable_money.setText(String.format("可领取金额：%1$.2f元", depositAmountResp.getCash() / 100d));
        comment_tv.setText(depositAmountResp.getComment());
    }

    @OnClick(value = {R.id.sub_title, R.id.deposit_commit})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.sub_title:
                startActivity(DepositRecordActivity.class);
                break;
            case R.id.deposit_commit:
                preDepositCommit();
                break;
        }
    }

    private void preDepositCommit() {

        if ("".equals(money) || "0".equals(money)) {
            showToast("请输入提现金额");
            return;
        }

//        if (Double.parseDouble(money) < 100) {
//            showToast("提现金额需满100");
//            return;
//        }

//        if (Double.parseDouble(money) > Double.parseDouble(allMoney) / 100) {
//            showToast("您输入的金额大于可提现金额");
//            return;
//        }

        showProgressDialog();
        UserInfoHandler.refresh("wallet");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveUserInfoResult(UserInfoResp userInfoResp) {
        if (!"wallet".equals(userInfoResp.getFrom()))
            return;
        String level = userInfoResp.getLevel();
        if (!("HIGH".equals(level) || "GOLD".equals(level))) {
            showToast("您还不是会员，请先成为会员");
            startActivity(VipUpgradeActivity.class);
            return;
        }
        String idCardNum = userInfoResp.getIdCardNum();
        String bankCardNum = userInfoResp.getBankCardNum();

        if (TextUtils.isEmpty(idCardNum) || TextUtils.isEmpty(bankCardNum)) {
            showToast("请完善身份证、银行卡信息");
            Bundle bundle = new Bundle();
            bundle.putString("from", "wallet");
            startActivity(PersonalInfoActivity.class, bundle);
            return;
        }
        depositCommit();
    }

    private void depositCommit() {
        DepositCommitParam param = new DepositCommitParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setAmount(money);
        param.setRsaValue(RSAUtil.getRSA(userId + token + money));
        showProgressDialog();
        RequestWrapper.depositCommit(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveDepositCommitResult(DepositCommitResp depositCommitResp) {
        String result = depositCommitResp.getResult();
        if ("SUCCESS".equals(result)) {
            showToast("提现成功");
            getDepositAmount();
        } else if ("RESUBMIT".equals(result)) {
            showToast("当天重复提交");
        } else {
            showToast("提现失败");
        }
    }
}
