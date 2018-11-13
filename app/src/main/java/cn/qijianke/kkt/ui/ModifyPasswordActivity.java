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
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.req.ModifyPasswordParam;
import cn.qijianke.kkt.model.resp.ModifyPasswordResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.utils.RSAUtil;

public class ModifyPasswordActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;

    @BindView(R.id.original_pwd)
    EditText original_pwd;
    @BindView(R.id.new_pwd)
    EditText new_pwd;
    @BindView(R.id.reinput_pwd)
    EditText reinput_pwd;

    String oldpwd = "";
    String newpwd = "";
    String renewpwd = "";

    @Override
    protected int getContentLayout() {
        return R.layout.modify_password_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("修改密码");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();

        original_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                oldpwd = s.toString();
            }
        });
        new_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                newpwd = s.toString();
            }
        });
        reinput_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                renewpwd = s.toString();
            }
        });

    }

    @OnClick(value = {R.id.confirm_modify_btn})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.confirm_modify_btn:
                submit();
                break;
        }
    }

    private void submit() {
        Pattern compile = Pattern.compile("[0-9a-zA-Z_]{6,16}");
        if (!compile.matcher(oldpwd).matches()) {
            showToast("请输入6至16位字母、数字和下划线组合的原始密码");
            return;
        }
        if (!compile.matcher(newpwd).matches()) {
            showToast("请输入6至16位字母、数字和下划线组合的新密码");
            return;
        }
        if (!renewpwd.equals(newpwd)) {
            showToast("新密码与确认密码不一致");
            return;
        }
        if (renewpwd.equals(oldpwd)) {
            showToast("新密码与原始密码相同");
            return;
        }

        modifyPwd();
    }

    private void modifyPwd() {
        ModifyPasswordParam param = new ModifyPasswordParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        param.setUserId(userId);
        param.setOldPassword(oldpwd);
        param.setNewPassword(newpwd);
        param.setRsaValue(RSAUtil.getRSA(userId + oldpwd + newpwd));
        showProgressDialog();
        RequestWrapper.modifyPassword(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveModifyPasswordResult(ModifyPasswordResp modifyPasswordResp) {
        showToast("修改成功");
    }

}
