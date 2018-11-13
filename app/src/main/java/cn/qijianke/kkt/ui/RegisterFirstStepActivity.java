package cn.qijianke.kkt.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.KKTApplication;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.common.Constants;
import cn.qijianke.kkt.model.resp.SmsResp;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.utils.ActivityUtil;
import cn.qijianke.kkt.utils.DeviceUtil;
import cn.qijianke.kkt.utils.RSAUtil;

public class RegisterFirstStepActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title_tv;
    @BindView(R.id.sub_title)
    TextView sub_title_tv;
    @BindView(R.id.graphic_img)
    ImageView graphic_certi_code_img;
    @BindView(R.id.edit_mobilephone)
    EditText edit_mobilephone;
    @BindView(R.id.edit_certi_code)
    EditText edit_certi_code;

    @Override
    protected int getContentLayout() {
        return R.layout.register_1_layout;
    }

    @Override
    protected void view() {
        title_tv.setText("注册");
        sub_title_tv.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        loadGraphicCertiCode();
        ActivityUtil.push(this);
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


    @OnClick(value = {R.id.next_step, R.id.graphic_img_content})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.graphic_img_content:
                loadGraphicCertiCode();
                break;
            case R.id.next_step:
                next_step();
                break;
        }
    }

    private void loadGraphicCertiCode() {
        String deViceId = DeviceUtil.getDeViceId(KKTApplication.getInstance());
        String url = Constants.HOST + "/checkcode/captcha?deviceId=" + deViceId + "&rsaValue=" + RSAUtil.getRSA(deViceId);
        Log.i(getClass().getName(), "wust===>imgUrl=" + url);
        Picasso.get().load(url).into(graphic_certi_code_img);
    }

    private void next_step() {
        String mobilephone = edit_mobilephone.getText().toString();
        String certicode = edit_certi_code.getText().toString();

        boolean matches = Pattern.compile("[0-9]{11}").matcher(mobilephone).matches();
        if (!matches) {
            showToast("请输入有效的11位手机号");
            return;
        }
        matches = Pattern.compile("[0-9]{4}").matcher(certicode).matches();
        if (!matches) {
            showToast("请输入4位图形验证码");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("mobilephone", mobilephone);
        bundle.putString("certiCode", certicode);
        startActivity(RegisterSecondStepActivity.class, bundle);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveSendSmsResult(SmsResp smsResp) {
        String status = smsResp.getStatus();
        if ("yes".equals(status)) {
            Bundle bundle = new Bundle();
            bundle.putString("mobilephone", edit_mobilephone.getText().toString());
            bundle.putString("certiCode", edit_certi_code.getText().toString());
            startActivity(RegisterSecondStepActivity.class, bundle);
        } else {
            showToast("您的图形验证码输入错误啦~");
        }
    }
}
