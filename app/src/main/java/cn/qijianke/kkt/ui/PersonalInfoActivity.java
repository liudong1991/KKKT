package cn.qijianke.kkt.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.req.UserInfoParam;
import cn.qijianke.kkt.model.resp.UserInfoResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.utils.RSAUtil;

public class PersonalInfoActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.personal_info_content)
    LinearLayout personal_info_content;

    String from;

    private UserInfoResp userInfoResp = new UserInfoResp();

    Integer[] imgs = {
            R.mipmap.personal_info_id, R.mipmap.personal_info_mobilephone,
            R.mipmap.personal_info_name, R.mipmap.personal_info_certi,
            R.mipmap.bank_name, R.mipmap.bank_no,
            R.mipmap.bind_email, R.mipmap.receive_address,
            R.mipmap.weixin_account, R.mipmap.zhifubao_account,
            R.mipmap.login_tjr_icon, R.mipmap.personal_info_mobilephone,
            R.mipmap.query_pos
    };

    String[] labels = {
            "账号ID", "手机号码", "姓名", "身份证号", "银行名称", "银行卡号", "绑定邮箱", "收货地址", "微信账号", "支付宝账号", "推荐人", "推荐人手机", "机具号"
    };

    String[] values = {
            "18996209426", "18996209426", "丫丫", "421081199011155628", "平安银行", "8897 **** **** 7654", "458216587@qq.com", "", "6521985478@qq.com", "8412657482@qq.com", "", "", ""
    };

    @Override
    protected int getContentLayout() {
        return R.layout.personal_info_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("个人资料");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();
        initValues();
        setContent();

        from = getIntent().getStringExtra("from");
        showProgressDialog();
    }

    @OnClick(value = {R.id.modify_btn})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.modify_btn:
                Bundle bundle = new Bundle();
                bundle.putParcelable("userInfoResp", userInfoResp);
                bundle.putString("from", from);
                startActivity(ModifyPersonalInfoActivity.class, bundle);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        userInfo();
    }

    private void initValues() {
        values = new String[]{
                userInfoResp.getId(), userInfoResp.getMobileNum(), userInfoResp.getName(), userInfoResp.getIdCardNum(), userInfoResp.getBankCardIssue(),
                userInfoResp.getBankCardNum(), userInfoResp.getEmail(), userInfoResp.getReceiveAddress(), userInfoResp.getWxPayId(), userInfoResp.getAlipayId(),
                userInfoResp.getLeaderName(), userInfoResp.getLeaderMobileNum(), userInfoResp.getPosId()
        };
    }

    private void setContent() {
        personal_info_content.removeAllViews();
        for (int i = 0; i < imgs.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.personal_info_item_layout, null);
            ((ImageView) view.findViewById(R.id.personal_info_item_img)).setImageResource(imgs[i]);
            ((TextView) view.findViewById(R.id.personal_info_item_label)).setText(labels[i]);
            TextView content = view.findViewById(R.id.personal_info_item_content);
            content.setText(values[i] == null ? "" : values[i]);
            if (i == 11) {
                content.setTextColor(Color.parseColor("#4F94CD"));
                final String phoneNumber = values[i];
                if (!TextUtils.isEmpty(phoneNumber)) {
                    content.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                            startActivity(dialIntent);
                        }
                    });
                }
            }
            personal_info_content.addView(view);
        }
    }

    private void userInfo() {
        UserInfoParam param = new UserInfoParam();
        param.setFrom("userInfo");
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setRsaValue(RSAUtil.getRSA(userId + token));
        RequestWrapper.getUserInfo(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveUserInfoResult(UserInfoResp userInfoResp) {
        if (!"userInfo".equals(userInfoResp.getFrom())) return;
        this.userInfoResp = userInfoResp;
        initValues();
        setContent();
    }
}
