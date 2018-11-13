package cn.qijianke.kkt.ui;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.common.Constants;
import cn.qijianke.kkt.model.req.RecommandMobilephoneParam;
import cn.qijianke.kkt.model.req.RegisterParam;
import cn.qijianke.kkt.model.req.SmsParam;
import cn.qijianke.kkt.model.resp.RecommandMobilephoneResp;
import cn.qijianke.kkt.model.resp.RegisterResp;
import cn.qijianke.kkt.model.resp.SmsResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.utils.ActivityUtil;
import cn.qijianke.kkt.utils.RSAUtil;

public class RegisterSecondStepActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title_tv;
    @BindView(R.id.sub_title)
    TextView sub_title_tv;
    @BindView(R.id.edit_certi_code)
    EditText edit_certi_code;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.edit_invitor)
    EditText edit_invitor;
    @BindView(R.id.get_sms_code)
    TextView get_sms_code;

    private String mobilephone;
    private String certiCode;

    @Override
    protected int getContentLayout() {
        return R.layout.register_2_layout;
    }

    @Override
    protected void view() {
        title_tv.setText("注册");
        sub_title_tv.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        mobilephone = getIntent().getStringExtra("mobilephone");
        certiCode = getIntent().getStringExtra("certiCode");
        ActivityUtil.push(this);

        edit_certi_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        edit_invitor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });

        getRecommandMobilephone();
    }

    @OnClick(value = {R.id.register_btn, R.id.get_sms_code})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.get_sms_code:
                getSms();
                break;
            case R.id.register_btn:
                register();
                break;
        }
    }

    private void getRecommandMobilephone() {
        RecommandMobilephoneParam param = new RecommandMobilephoneParam();
        showProgressDialog();
        RequestWrapper.getRecommandMobilephone(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveRecommandMobilephoneResult(RecommandMobilephoneResp recommandMobilephoneResp) {
        String refereeNo = recommandMobilephoneResp.getRefereeNo();
        edit_invitor.setText(refereeNo == null ? "" : refereeNo);
        edit_invitor.setEnabled(TextUtils.isEmpty(refereeNo));
    }

    private void getSms() {
        SmsParam smsParam = new SmsParam();
        String tag = Constants.REGISTER;
        smsParam.setUserPhone(mobilephone);
        smsParam.setrSign(tag);
        smsParam.setCaptchaText(certiCode);
        smsParam.setRsaValue(RSAUtil.getRSA(mobilephone + tag + certiCode));

        showProgressDialog();
        RequestWrapper.sendSms(smsParam);
    }

    private void register() {
        RegisterParam param = new RegisterParam();
        String certiCode = edit_certi_code.getText().toString();
        String password = edit_password.getText().toString();
        String refereeNo = edit_invitor.getText().toString();

        if (mobilephone.equals(refereeNo)) {
            showToast("推荐人手机号码不能与注册手机号相同");
            return;
        }

        boolean matches = Pattern.compile("[0-9a-zA-Z_]{6,16}").matcher(password).matches();
        if (!matches) {
            showToast("请输入6至16位字母、数字和下划线组合的密码");
            return;
        }

        param.setUserPhone(mobilephone);
        param.setVerificationCode(certiCode);
        param.setUserPwd(password);
        param.setRefereeNo(refereeNo);
        param.setRsaValue(RSAUtil.getRSA(mobilephone + password + certiCode + refereeNo));

        showProgressDialog();
        RequestWrapper.register(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveSendSmsResult(SmsResp smsResp) {
        String status = smsResp.getStatus();
        if ("yes".equals(status)) {
            showToast("短信验证码请求发送成功！");
        } else {
            showToast("您的图形验证码输入错误啦~");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveRegisterResult(RegisterResp registerResp) {
        showToast("注册成功");
        ActivityUtil.clear();
        LoginActivity.mobilephone = mobilephone;
    }
}
