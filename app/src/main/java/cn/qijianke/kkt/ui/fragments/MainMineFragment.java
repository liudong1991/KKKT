package cn.qijianke.kkt.ui.fragments;

import android.content.Intent;
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
import cn.qijianke.kkt.model.req.LogoutParam;
import cn.qijianke.kkt.model.resp.LogoutResp;
import cn.qijianke.kkt.model.resp.UserInfoResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.AboutUsActivity;
import cn.qijianke.kkt.ui.ApplyPosActivity;
import cn.qijianke.kkt.ui.ContactCustomerServiceActivity;
import cn.qijianke.kkt.ui.LoginActivity;
import cn.qijianke.kkt.ui.ModifyPasswordActivity;
import cn.qijianke.kkt.ui.PersonalInfoActivity;
import cn.qijianke.kkt.ui.SuggestionActivity;
import cn.qijianke.kkt.ui.UpgradeActivity;
import cn.qijianke.kkt.ui.VipUpgradeActivity;
import cn.qijianke.kkt.ui.base.BaseFragment;
import cn.qijianke.kkt.ui.handlers.UserInfoHandler;
import cn.qijianke.kkt.utils.RSAUtil;

public class MainMineFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.mine_name)
    TextView mine_name;
    @BindView(R.id.mine_mobilephone)
    TextView mine_mobilephone;
    @BindView(R.id.vip_label)
    ImageView vip_label;

    Integer[] imgs = {
            R.mipmap.person_info, R.mipmap.vip_upgrade,
            R.mipmap.pos_upgrade, R.mipmap.sugguestion,
            R.mipmap.contact_client_service, R.mipmap.password_safe,
            R.mipmap.version_update, R.mipmap.about_us
    };

    String[] names = {
            "个人资料", "会员升级", "机具申请", "反馈意见", "联系客服", "密码安全", "版本升级", "关于我们"
    };

    @Override
    protected int getContentLayout() {
        return R.layout.mine_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("我的");
        sub_title.setText("注销登录");
        String userName = Session.getSession().getLoginResp().getUserName();
        mine_name.setText(TextUtils.isEmpty(userName) ? "" : userName);
        mine_mobilephone.setText(Session.getSession().getLoginResp().getUserPhone());
    }

    @Override
    protected void init() {
        for (int i = 0; i < imgs.length; i++) {
            View item = getLayoutInflater().inflate(R.layout.mine_item_layout, null);
            ((ImageView) item.findViewById(R.id.mine_item_logo)).setImageResource(imgs[i]);
            ((TextView) item.findViewById(R.id.mine_item_label)).setText(names[i]);
            item.setTag(i);
            item.setOnClickListener(clickListener);
            content.addView(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Session.getSession().isLogin()) {
            UserInfoHandler.refresh("mine");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveUserInfoResult(UserInfoResp userInfoResp) {
        if (!"mine".equals(userInfoResp.getFrom())) return;
        String userName = userInfoResp.getName();
        String level = userInfoResp.getLevel();
        String level_b = "HIGH".equals(level) ? "高级会员" : ("GOLD".equals(level) ? "黄金会员" : "普通会员");
        mine_name.setText((TextUtils.isEmpty(userName) ? "用户" : userName) + "(" + level_b + ")");
        vip_label.setImageResource("HIGH".equals(level) ? R.mipmap.hign_vip : ("GOLD".equals(level) ? R.mipmap.gold_vip : R.mipmap.normal_vip));
    }

    @OnClick(value = {R.id.sub_title})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.sub_title:
                logout();
                break;
        }
    }

    public void logout() {
        LogoutParam param = new LogoutParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setRsaValue(RSAUtil.getRSA(userId + token));

        showProgressDialog();
        RequestWrapper.logout(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logoutResult(LogoutResp logoutResp) {
        Session.getSession().logout();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getTag() instanceof Integer) {
                switch ((int) view.getTag()) {
                    case 0:
                        startActivity(PersonalInfoActivity.class);
                        break;
                    case 1:
                        startActivity(VipUpgradeActivity.class);
                        break;
                    case 2:
                        startActivity(ApplyPosActivity.class);
                        break;
                    case 3:
                        startActivity(SuggestionActivity.class);
                        break;
                    case 4:
                        startActivity(ContactCustomerServiceActivity.class);
                        break;
                    case 5:
                        startActivity(ModifyPasswordActivity.class);
                        break;
                    case 6:
                        startActivity(UpgradeActivity.class);
                        break;
                    case 7:
                        startActivity(AboutUsActivity.class);
                        break;
                }
            }
        }
    };
}
