package cn.qijianke.kkt.ui.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public abstract class BaseActivity extends AppCompatActivity {

    private CustomProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarStyle();
        setContentView(getContentLayout());
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        view();
        init();
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
            this.dialog = new CustomProgressDialog(this);
        }
        this.dialog.setTips("加载中...");
        if (!this.dialog.isShowing()) {
            this.dialog.show();
        }
    }

    private void setStatusBarStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN /*| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION */ | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //给状态栏设置颜色。我设置透明。
            window.setStatusBarColor(Color.TRANSPARENT);
            //window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    protected void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
