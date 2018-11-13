package cn.qijianke.kkt.utils;

import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.qijianke.kkt.KKTApplication;
import cn.qijianke.kkt.common.Constants;

public class ImageUtil {

    public static void adjustImg(ImageView iv, int resId) {
        adjustImg(iv, resId, 0);
    }

    public static void adjustImg(ImageView iv, int resId, int padding) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(KKTApplication.getInstance().getResources(), resId, options);
        int imgWidth = options.outWidth;
        int imgHeight = options.outHeight;
        int rImgHeight = (int) ((Constants.SCREEN_WIDTH - 2 * DensityUtil.dp2px(padding)) * 1.0 / imgWidth * imgHeight);
        ViewGroup.LayoutParams lp = iv.getLayoutParams();
        lp.width = Constants.SCREEN_WIDTH;
        lp.height = rImgHeight;
        iv.setLayoutParams(lp);
    }

}
