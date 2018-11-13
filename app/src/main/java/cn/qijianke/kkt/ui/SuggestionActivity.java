package cn.qijianke.kkt.ui;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.req.SuggestParam;
import cn.qijianke.kkt.model.resp.SuggestResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.utils.RSAUtil;

public class SuggestionActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.tv_count)
    TextView tv_count;

    @Override
    protected int getContentLayout() {
        return R.layout.suggestion_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("反馈意见");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() <= 200) {
                    tv_count.setText(editable.length() + "/200");
                } else {
                    editable.delete(200, editable.length());
                    tv_count.setText("200/200");
                }
            }
        });
        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
    }

    @OnClick(value = {R.id.suggestion_btn})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.suggestion_btn:
                suggest();
                break;
        }
    }

    private void suggest() {
        String content = edit.getText().toString();
        if (TextUtils.isEmpty(content)) {
            showToast("反馈内容不能为空");
            return;
        }

        SuggestParam param = new SuggestParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();

        param.setUserId(userId);
        param.setToken(token);
        param.setFeedbackContent(content);
        param.setRsaValue(RSAUtil.getRSA(userId + token + content));
        showProgressDialog();
        RequestWrapper.suggest(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void suggestResult(SuggestResp suggestResp) {
        showToast("您的反馈提交成功");
    }
}
