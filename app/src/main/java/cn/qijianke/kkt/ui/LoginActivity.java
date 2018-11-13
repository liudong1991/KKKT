package cn.qijianke.kkt.ui;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.MainActivity;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.req.LoginParam;
import cn.qijianke.kkt.model.resp.LoginResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.utils.ActivityUtil;
import cn.qijianke.kkt.utils.RSAUtil;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title_tv;
    @BindView(R.id.sub_title)
    TextView sub_title_tv;
    @BindView(R.id.edit_mobilephone)
    EditText edit_mobilephone;
    @BindView(R.id.edit_password)
    EditText edit_password;

    public static String mobilephone = "";

    @Override
    protected int getContentLayout() {
        return R.layout.login_layout;
    }

    @Override
    protected void view() {
        title_tv.setText("登录");
        sub_title_tv.setText("注册");
    }

    @Override
    protected void init() {

        if (Session.getSession().isLogin()) {
            startActivity(MainActivity.class);
            finish();
        }

        edit_mobilephone.addTextChangedListener(new TextWatcher() {

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
            }
        });
    }

    @OnClick(value = {R.id.sub_title, R.id.forget_passwrod_link, R.id.login_btn})
    public void haldle(View view) {
        switch (view.getId()) {
            case R.id.sub_title:
                startActivity(RegisterFirstStepActivity.class);
                break;
            case R.id.forget_passwrod_link:
                startActivity(ForgetPasswordFirstStepActivity.class);
                break;
            case R.id.login_btn:
                login();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityUtil.clear();
        edit_mobilephone.setText(mobilephone);
    }

    private void login() {
        LoginParam param = new LoginParam();

        String mobilephone = edit_mobilephone.getText().toString();
        String password = edit_password.getText().toString();

        // boolean matches = Pattern.compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$").matcher(mobilephone).matches();
        boolean matches = Pattern.compile("^\\d{11}$").matcher(mobilephone).matches();
        if (!matches) {
            showToast("请输入有效的11位手机号");
            return;
        }
        matches = Pattern.compile("[0-9a-zA-Z_]{6,16}").matcher(password).matches();
        if (!matches) {
            showToast("请输入6至16位字母、数字和下划线组合的密码");
            return;
        }

        param.setUserPhone(mobilephone);
        param.setUserPwd(password);
        param.setRsaValue(RSAUtil.getRSA(mobilephone + password));

        showProgressDialog();
        RequestWrapper.login(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveLoginResult(LoginResp loginResp) {
        Session.getSession().login(loginResp);
        startActivity(MainActivity.class);
        finish();
    }
}
