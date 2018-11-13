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

public class PayTypeTabView extends RelativeLayout {

    public static Integer type;

    ImageView payLogo;
    TextView payLabel;
    ImageView checkImg;

    Drawable select_bg;
    Drawable unselect_bg;
    Drawable payLogo_bg;

    String pay_label_str;

    Integer localType;

    boolean selected;

    public PayTypeTabView(Context context) {
        super(context);
    }

    public PayTypeTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PayTypeTabView);
        select_bg = a.getDrawable(R.styleable.PayTypeTabView_pay_select_btn_bg);
        unselect_bg = a.getDrawable(R.styleable.PayTypeTabView_pay_unselect_btn_bg);
        payLogo_bg = a.getDrawable(R.styleable.PayTypeTabView_pay_logo);
        pay_label_str = a.getString(R.styleable.PayTypeTabView_pay_label);
        localType = a.getInteger(R.styleable.PayTypeTabView_pay_type_no, 1);
        selected = a.getBoolean(R.styleable.PayTypeTabView_pay_selected, false);
        init();
    }


    private void init() {
        setBackgroundColor(Color.WHITE);
        setGravity(Gravity.CENTER_VERTICAL);
        setPadding(32, 24, 32, 24);

        payLogo = new ImageView(getContext());
        payLogo.setImageDrawable(payLogo_bg);
        payLogo.setId(0x1002);

        payLabel = new TextView(getContext());
        payLabel.setTextColor(Color.parseColor("#333333"));
        payLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        payLabel.setText(pay_label_str);

        checkImg = new ImageView(getContext());
        checkImg.setImageDrawable(unselect_bg);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        addView(payLogo, lp);
        lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        lp.addRule(RelativeLayout.RIGHT_OF, payLogo.getId());
        lp.leftMargin = 20;
        addView(payLabel, lp);
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
                    if (childAt instanceof PayTypeTabView) {
                        ((PayTypeTabView) childAt).select(childAt == PayTypeTabView.this);
                    }
                }
            }
        });

        localType = 1;
        select(selected);
    }

    public void select(boolean isSelect) {
        checkImg.setImageDrawable(isSelect ? select_bg : unselect_bg);
        type = localType;
    }

}
