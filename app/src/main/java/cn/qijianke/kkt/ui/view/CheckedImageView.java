package cn.qijianke.kkt.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.qijianke.kkt.R;

public class CheckedImageView extends LinearLayout implements Checkable {

    private ImageView img;
    private TextView tv;

    private Bitmap checked_bg;
    private Bitmap unchecked_bg;
    private String label;
    private int text_color;

    private boolean isChecked = false;

    private OnCheckedListener onCheckedListener;

    private Context context;

    public CheckedImageView(Context context) {
        super(context);
    }

    public CheckedImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CheckedImageView);

        int checked_bg_rid = a.getResourceId(R.styleable.CheckedImageView_checked_bg, 0);
        int unchecked_bg_rid = a.getResourceId(R.styleable.CheckedImageView_unchecked_bg, 0);
        label = a.getString(R.styleable.CheckedImageView_tab_label);
        checked_bg = BitmapFactory.decodeResource(getResources(), checked_bg_rid);
        unchecked_bg = BitmapFactory.decodeResource(getResources(), unchecked_bg_rid);
        text_color = a.getColor(R.styleable.CheckedImageView_text_color, 0xFFFFFF);
        init();
    }

    public CheckedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void init() {
        img = new ImageView(context);
        tv = new TextView(context);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        addView(img);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = 10;
        addView(tv, lp);
        setImageDrawable();

        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        tv.setText(label);
        tv.setTextColor(text_color);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                isChecked = true;
                if (onCheckedListener != null) {
                    onCheckedListener.check(CheckedImageView.this);
                }
            }
        });
    }

    @Override
    public void setChecked(boolean b) {
        isChecked = b;
        setImageDrawable();
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        isChecked = !isChecked;
        setImageDrawable();
    }

    public void setImageDrawable() {
        post(new Runnable() {
            @Override
            public void run() {
                img.setImageBitmap(isChecked ? checked_bg : unchecked_bg);
            }
        });
    }

    public interface OnCheckedListener {
        void check(View view);
    }

    public void setOnCheckedListener(OnCheckedListener onCheckedListener) {
        this.onCheckedListener = onCheckedListener;
    }
}
