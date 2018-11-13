package cn.qijianke.kkt.ui;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnLongClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.ui.base.BaseActivity;

public class ContactCustomerServiceActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;

    @Override
    protected int getContentLayout() {
        return R.layout.contact_customer_service_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("联系客服");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();

    }

    @OnLongClick(value = {R.id.weixin_qr_code, R.id.qq_qr_code})
    public boolean longClick(View view) {
        /*ImageView imgV = (ImageView) view;
        Bitmap bitmap = ((BitmapDrawable) imgV.getDrawable()).getBitmap();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] data = new int[width * height];
        bitmap.getPixels(data, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result re = null;
        try {
            re = reader.decode(binaryBitmap);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        if (re == null) return true;
        String text = re.getText();
        Log.i(getClass().getName(), "wust==>text:" + text);*/
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        String text = "";
        switch (view.getId()) {
            case R.id.weixin_qr_code:
                text = "kaka1341kaka";
                break;
            case R.id.qq_qr_code:
                text = "KKT1413C";
                break;
        }
        // 将文本内容放到系统剪贴板里。
        cm.setText(text);
        showToast("客服微信号：" + text + ",已复制到剪贴板里");

        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            showToast("检查到您手机没有安装微信，请安装后使用该功能");
        }
        return true;
    }

}
