package cn.qijianke.kkt.ui;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import cn.qijianke.kkt.KKTApplication;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.utils.VersionUtil;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.version)
    TextView version;

    @Override
    protected int getContentLayout() {
        return R.layout.about_us_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("关于我们");
        sub_title.setVisibility(View.GONE);
        version.setText("版本v" + VersionUtil.getVersion(KKTApplication.getInstance()));
    }
}
