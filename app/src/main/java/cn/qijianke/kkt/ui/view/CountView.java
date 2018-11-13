package cn.qijianke.kkt.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.qijianke.kkt.R;

public class CountView extends FrameLayout {

    int countNum = 0;
    TextView count;
    TextView minus;
    TextView plus;

    public CountView(@NonNull Context context) {
        super(context);
    }

    public CountView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.count_handler_layout, null);
        count = view.findViewById(R.id.count);
        minus = view.findViewById(R.id.minus);
        plus = view.findViewById(R.id.plus);
        addView(view);

        minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countNum > 0) {
                    setCountNum(--countNum);
                    if (onCountNumChangeListener != null)
                        onCountNumChangeListener.afterChanged(countNum);
                }
            }
        });

        plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountNum(++countNum);
                if (onCountNumChangeListener != null)
                    onCountNumChangeListener.afterChanged(countNum);
            }
        });

        setCountNum(countNum);
    }

    public int getCountNum() {
        return countNum;
    }

    public void setCountNum(int countNum) {
        if (countNum < 0) return;
        this.countNum = countNum;
        count.setText(countNum + "");
        minus.setTextColor(countNum == 0 ? Color.parseColor("#909090") : Color.BLACK);
    }

    private OnCountNumChangeListener onCountNumChangeListener;

    public void setOnCountNumChangeListener(OnCountNumChangeListener onCountNumChangeListener) {
        this.onCountNumChangeListener = onCountNumChangeListener;
    }

    public interface OnCountNumChangeListener {
        void afterChanged(int countNum);
    }
}
