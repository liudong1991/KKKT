package cn.qijianke.kkt.ui;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.req.ModifyUserInfoParam;
import cn.qijianke.kkt.model.resp.ModifyUserInfoResp;
import cn.qijianke.kkt.model.resp.UserInfoResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.utils.RSAUtil;

public class ModifyPersonalInfoActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.modify_personal_info_content)
    LinearLayout modify_personal_info_content;
    @BindView(R.id.edit_address)
    EditText edit_address;

    UserInfoResp userInfoResp;

    String[] labels = {
            "账号ID：", "姓名：", "身份证号：", "银行名称：", "银行卡号：", "手机号码：", "绑定邮箱：", "微信：", "支付宝：", "机具号："
    };

    String[] values;

    String from;

    @Override
    protected int getContentLayout() {
        return R.layout.modify_personal_info_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("修改资料");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();

        userInfoResp = getIntent().getParcelableExtra("userInfoResp");
        from = getIntent().getStringExtra("from");

        initValues();

        for (int i = 0; i < labels.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.modify_personal_info_item_layout, null);
            ((TextView) view.findViewById(R.id.label)).setText(labels[i]);
            EditText input = view.findViewById(R.id.input);
            input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    return (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER);
                }
            });
            input.setText(values[i] == null ? "" : values[i]);
            if (i == 0 || i == 5 || (i == 9 && !TextUtils.isEmpty(values[9]))) {
                input.setEnabled(false);
                input.setTextColor(Color.parseColor("#666666"));
            }
            final int index = i;
            input.addTextChangedListener(new TextWatcher() {

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
                    if (index == 10) {
                        int length = editable.length();
                        if (length > 16) {
                            editable.delete(0, editable.length());
                            editable.append(old);
                        }
                    }
                    values[index] = editable.toString();
                }
            });
            modify_personal_info_content.addView(view);
        }

        edit_address.setText(userInfoResp.getReceiveAddress() == null ? "" : userInfoResp.getReceiveAddress());
        edit_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                values[values.length - 1] = editable.toString();
            }
        });
        edit_address.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
    }

    @OnClick(value = {R.id.modify_btn})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.modify_btn:
                modifyUserInfo();
                break;
        }
    }

    private void initValues() {
        values = new String[]{
                userInfoResp.getId(), userInfoResp.getName(), userInfoResp.getIdCardNum(), userInfoResp.getBankCardIssue(),
                userInfoResp.getBankCardNum(), userInfoResp.getMobileNum(), userInfoResp.getEmail(),
                userInfoResp.getWxPayId(), userInfoResp.getAlipayId(), userInfoResp.getPosId(), userInfoResp.getReceiveAddress()
        };
    }

    private void modifyUserInfo() {
        ModifyUserInfoParam param = new ModifyUserInfoParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        String name = values[1];
        String idCardNum = values[2];
        String bankCardNum = values[4];
        String bankCardIssue = values[3];
        String mobileNum = values[5];
        String receiveAddress = values[10];
        String email = values[6];
        String wxPayId = values[7];
        String alipayId = values[8];
        String posId = values[9];
        String rsaValue = RSAUtil.getRSA(userId + token + name + idCardNum + bankCardNum + bankCardIssue + mobileNum + receiveAddress + email + wxPayId + alipayId + posId);
        param.setUserId(userId);
        param.setToken(token);
        param.setName(name);
        param.setIdCardNum(idCardNum);
        param.setBankCardNum(bankCardNum);
        param.setBankCardIssue(bankCardIssue);
        param.setMobileNum(mobileNum);
        param.setReceiveAddress(receiveAddress);
        param.setEmail(email);
        param.setWxPayId(wxPayId);
        param.setAlipayId(alipayId);
        param.setPosId(posId);
        param.setRsaValue(rsaValue);

        if ("wallet".equals(from)) {
            if (TextUtils.isEmpty(idCardNum) || TextUtils.isEmpty(bankCardNum)) {
                showToast("必须输入身份证号和银行信息");
                return;
            }
        }

        showProgressDialog();
        RequestWrapper.modifyUserInfo(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveModifyUserInfoResult(ModifyUserInfoResp modifyUserInfoResp) {
        Session.getSession().getLoginResp().setUserName(values[1]);
        Session.getSession().saveLoginResp2Disk();
        showToast("修改成功");
    }
}
