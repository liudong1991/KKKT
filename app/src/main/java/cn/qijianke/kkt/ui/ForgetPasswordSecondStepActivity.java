package cn.qijianke.kkt.ui;

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
import cn.qijianke.kkt.model.req.ResetPasswordParam;
import cn.qijianke.kkt.model.req.SmsParam;
import cn.qijianke.kkt.model.resp.ResetPasswordResp;
import cn.qijianke.kkt.model.resp.SmsResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.utils.ActivityUtil;
import cn.qijianke.kkt.utils.RSAUtil;

public class ForgetPasswordSecondStepActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title_tv;
    @BindView(R.id.sub_title)
    TextView sub_title_tv;
    @BindView(R.id.edit_certi_code)
    EditText edit_certi_code;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.get_sms_code)
    TextView get_sms_code;

    private String mobilephone;
    private String certiCode;

    @Override
    protected int getContentLayout() {
        return R.layout.forget_password_2_layout;
    }

    @Override
    protected void view() {
        title_tv.setText("忘记密码");
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
    }

    @OnClick(value = {R.id.confirm_modify_btn, R.id.get_sms_code})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.get_sms_code:
                getSms();
                break;
            case R.id.confirm_modify_btn:
                modify();
                break;
        }
    }

    private void getSms() {
        SmsParam smsParam = new SmsParam();
        String tag = Constants.RESET;
        smsParam.setUserPhone(mobilephone);
        smsParam.setrSign(tag);
        smsParam.setCaptchaText(certiCode);
        smsParam.setRsaValue(RSAUtil.getRSA(mobilephone + tag + certiCode));

        showProgressDialog();
        RequestWrapper.sendSms(smsParam);
    }

    private void modify() {
        ResetPasswordParam param = new ResetPasswordParam();
        String certiCode = edit_certi_code.getText().toString();
        String password = edit_password.getText().toString();

        boolean matches = Pattern.compile("[0-9a-zA-Z_]{6,16}").matcher(password).matches();
        if (!matches) {
            showToast("请输入6至16位字母、数字和下划线组合的密码");
            return;
        }

        param.setUserPhone(mobilephone);
        param.setUserPwd(password);
        param.setVerificationCode(certiCode);
        param.setRsaValue(RSAUtil.getRSA(mobilephone + password + certiCode));

        showProgressDialog();
        RequestWrapper.resetPassword(param);
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
    public void receiveResetPasswordResult(ResetPasswordResp resetPasswordResp) {
        showToast("重置密码成功");
        ActivityUtil.clear();
    }
}
