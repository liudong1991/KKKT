package cn.qijianke.kkt.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.qijianke.kkt.R;
import cn.qijianke.kkt.utils.DensityUtil;

public class BenefitTabView extends LinearLayout {


    CharSequence[] labels = {"个人收益", "团队收益", "推广收益", "兑换收益"};
    float textsize;
    int selected_color;
    int unselected_color;
    boolean show_space_v;
    boolean is_switch;

    public BenefitTabView(@NonNull Context context) {
        super(context);
    }

    public BenefitTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BenefitTabView);
        labels = a.getTextArray(R.styleable.BenefitTabView_benefit_tab_labels);
        textsize = a.getDimensionPixelSize(R.styleable.BenefitTabView_benefit_tab_textsize, DensityUtil.sp2px(12));
        selected_color = a.getColor(R.styleable.BenefitTabView_benefit_tab_selected_color, Color.parseColor("#5399E5"));
        unselected_color = a.getColor(R.styleable.BenefitTabView_benefit_tab_unselected_color, Color.parseColor("#333333"));
        show_space_v = a.getBoolean(R.styleable.BenefitTabView_benefit_tab_show_space_v, true);
        is_switch = a.getBoolean(R.styleable.BenefitTabView_benefti_tab_switch, true);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        //setPadding(0, 30, 0, 30);
        setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams lp;
        for (int i = 0; i < labels.length; i++) {
            TextView tv = new TextView(getContext());
            tv.setTag(i);
            tv.setText(labels[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textsize);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(10, 30, 10, 30);
            lp = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.weight = 1;
            tv.setLayoutParams(lp);
            addView(tv);
            if (show_space_v) {
                View space_v = new View(getContext());
                space_v.setBackgroundColor(Color.parseColor("#333333"));
                lp = new LayoutParams(DensityUtil.dp2px(1), DensityUtil.dp2px(10));
                space_v.setLayoutParams(lp);
                addView(space_v);
            }
        }
        if (show_space_v)
            removeViewAt(getChildCount() - 1);

        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (child instanceof TextView) {
                if (is_switch) {
                    ((TextView) child).setTextColor(i == 0 ? selected_color : unselected_color);
                    child.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (int j = 0; j < getChildCount(); j++) {
                                View c = getChildAt(j);
                                if (c instanceof TextView)
                                    ((TextView) c).setTextColor(c == view ? selected_color : unselected_color);
                            }
                            if (onTabChangedListener != null)
                                onTabChangedListener.onChange(view);
                        }
                    });
                } else {
                    ((TextView) child).setTextColor(unselected_color);
                }
            }
        }
    }

    private OnTabChangedListener onTabChangedListener;

    public interface OnTabChangedListener {
        void onChange(View view);
    }

    public void setOnTabChangedListener(OnTabChangedListener onTabChangedListener) {
        this.onTabChangedListener = onTabChangedListener;
    }
}
