package cn.qijianke.kkt.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.qijianke.kkt.R;

public class VipTabView extends RelativeLayout {

    public static Integer type;

    TextView vipType;
    TextView price;
    ImageView checkImg;

    Drawable select_bg;
    Drawable unselect_bg;

    String vip_type;
    String vip_price;

    Integer localType;

    boolean selected;

    public VipTabView(Context context) {
        super(context);
    }

    public VipTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VipTabView);
        select_bg = a.getDrawable(R.styleable.VipTabView_select_btn_bg);
        unselect_bg = a.getDrawable(R.styleable.VipTabView_unselect_btn_bg);
        vip_type = a.getString(R.styleable.VipTabView_vip_type);
        vip_price = a.getString(R.styleable.VipTabView_vip_price);
        localType = a.getInteger(R.styleable.VipTabView_vip_type_no, 1);
        selected = a.getBoolean(R.styleable.VipTabView_vip_selected, false);
        init();
    }


    private void init() {
        setBackgroundColor(Color.WHITE);
        setGravity(Gravity.CENTER_VERTICAL);
        setPadding(32, 24, 32, 24);
        vipType = new TextView(getContext());
        vipType.setTextColor(Color.parseColor("#333333"));
        vipType.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        vipType.setText(vip_type);
        vipType.setId(0x1001);

        price = new TextView(getContext());
        price.setTextColor(Color.parseColor("#F67979"));
        price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        price.setText(vip_price);

        checkImg = new ImageView(getContext());
        checkImg.setImageDrawable(unselect_bg);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        addView(vipType, lp);
        lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        lp.addRule(RelativeLayout.RIGHT_OF, vipType.getId());
        lp.leftMargin = 20;
        addView(price, lp);
        lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        addView(checkImg, lp);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup parent = ((ViewGroup) getParent());
                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = parent.getChildAt(i);
                    if (childAt instanceof VipTabView) {
                        ((VipTabView) childAt).select(childAt == VipTabView.this);
                    }
                }
            }
        });
        type = 1;
        select(selected);
    }

    public void select(boolean isSelect) {
        checkImg.setImageDrawable(isSelect ? select_bg : unselect_bg);
        if (isSelect)
            type = localType;
    }

    public void setVip_price(String vip_price) {
        this.vip_price = vip_price;
        price.setText(vip_price);
    }

}
