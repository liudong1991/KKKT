package cn.qijianke.kkt.ui.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.req.ShareUrlParam;
import cn.qijianke.kkt.model.resp.ShareUrlResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.ShareQRcodeActivity;
import cn.qijianke.kkt.ui.base.BaseFragment;
import cn.qijianke.kkt.utils.ImageUtil;
import cn.qijianke.kkt.utils.RSAUtil;

public class MainSpreadFragment extends BaseFragment {

    @BindView(R.id.spread_img)
    ImageView spread_img;

    @Override
    protected int getContentLayout() {
        return R.layout.spread_layout;
    }

    @Override
    protected void view() {
        super.view();
    }

    @Override
    protected void init() {
        super.init();
        ImageUtil.adjustImg(spread_img, R.mipmap.spread1);
    }

    @OnClick(value = {R.id.share_btn})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.share_btn:
                getShareUrl();
                break;
        }
    }

    private void getShareUrl() {
        ShareUrlParam param = new ShareUrlParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setRsaValue(RSAUtil.getRSA(userId + token));
        showProgressDialog();
        RequestWrapper.getShareUrl(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveShareUrlResult(ShareUrlResp shareUrlResp) {
        String url = shareUrlResp.getUrl();
        if (TextUtils.isEmpty(url)) return;
        Bundle bundle = new Bundle();
        bundle.putString("share_url", url);
        startActivity(ShareQRcodeActivity.class, bundle);
    }

}
