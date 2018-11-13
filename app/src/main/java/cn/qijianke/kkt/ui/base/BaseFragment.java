package cn.qijianke.kkt.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import cn.qijianke.kkt.MainActivity;
import cn.qijianke.kkt.common.Constants;
import cn.qijianke.kkt.model.event.LoginExpireEvent;
import cn.qijianke.kkt.model.event.RequestFinishEvent;
import cn.qijianke.kkt.ui.view.CustomProgressDialog;

public abstract class BaseFragment extends Fragment {

    private CustomProgressDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayout(), null);
        ButterKnife.bind(this, view);
        view();
        init();
        return view;
    }

    protected abstract int getContentLayout();

    protected void view() {

    }

    protected void init() {

    }

    public void dismissProgressDialog() {
        if (this.dialog != null) {
            if (this.dialog.isShowing())
                this.dialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (this.dialog == null) {
            this.dialog = new CustomProgressDialog(getContext());
        }
        this.dialog.setTips("加载中...");
        if (!this.dialog.isShowing()) {
            this.dialog.show();
        }
    }

    protected void showToast(String content) {
        Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestFinish(RequestFinishEvent ev) {
        dismissProgressDialog();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginExpire(LoginExpireEvent ev) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EVENT_KEY, Constants.LOGIN_EXPIRE_EVENT);
        startActivity(MainActivity.class, bundle);
    }

    protected void startActivity(Class clazz) {
        startActivity(clazz, null);
    }

    protected void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
