package cn.qijianke.kkt.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import cn.qijianke.kkt.R;

public class ImageViewWrapper extends ImageView {

    boolean ischecked = false;
    Drawable checked_bg;
    Drawable unchecked_bg;


    public ImageViewWrapper(Context context) {
        super(context);
    }

    public ImageViewWrapper(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageViewWrapper);
        checked_bg = a.getDrawable(R.styleable.ImageViewWrapper_imgWrapper_checked_bg);
        unchecked_bg = a.getDrawable(R.styleable.ImageViewWrapper_imgWrapper_unchecked_bg);
        ischecked = a.getBoolean(R.styleable.ImageViewWrapper_imgWrapper_checked, false);
        init();
    }

    private void init() {
        check(ischecked);
    }

    public boolean isChecked() {
        return ischecked;
    }

    public void check() {
        ischecked = !ischecked;
        check(ischecked);
    }

    public void check(boolean ischecked) {
        setImageDrawable(ischecked ? checked_bg : unchecked_bg);
    }
}
