package cn.qijianke.kkt.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.utils.ImageUtil;

public class ShareQRcodeActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.qr_img)
    ImageView qr_img;
    @BindView(R.id.banner)
    ImageView banner;

    @Override
    protected int getContentLayout() {
        return R.layout.share_qrcode_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("介绍人二维码");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();
        ImageUtil.adjustImg(banner, R.mipmap.share_banner);
        String share_url = getIntent().getStringExtra("share_url");
        Bitmap mBitmap = CodeUtils.createImage(share_url, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.logo_copy));
        qr_img.setImageBitmap(mBitmap);
    }
}
