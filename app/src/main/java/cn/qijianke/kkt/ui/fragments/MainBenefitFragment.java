package cn.qijianke.kkt.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.ui.BenefitDetailActivity;
import cn.qijianke.kkt.ui.MyWalletActivity;
import cn.qijianke.kkt.ui.ParterActivity;
import cn.qijianke.kkt.ui.QueryDealActivity;
import cn.qijianke.kkt.ui.QueryExchangeActivity;
import cn.qijianke.kkt.ui.QueryPosActivity;
import cn.qijianke.kkt.ui.base.BaseFragment;
import cn.qijianke.kkt.utils.ImageUtil;

public class MainBenefitFragment extends BaseFragment {

    @BindView(R.id.home_ad)
    ImageView home_ad;
    @BindView(R.id.first_layout)
    RelativeLayout first_layout;
    @BindView(R.id.second_layout)
    RelativeLayout second_layout;

    @Override
    protected int getContentLayout() {
        return R.layout.benefit_layout;
    }

    @Override
    protected void init() {
        super.init();
        for (int i = 0; i < first_layout.getChildCount(); i++) {
            View child = first_layout.getChildAt(i);
            child.setTag(i);
            child.setOnClickListener(clickListener);
        }
        for (int i = 0; i < second_layout.getChildCount(); i++) {
            View child = second_layout.getChildAt(i);
            child.setTag(i + 3);
            child.setOnClickListener(clickListener);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageUtil.adjustImg(home_ad, R.mipmap.banner);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getTag() instanceof Integer) {
                switch ((int) view.getTag()) {
                    case 0:
                        startActivity(BenefitDetailActivity.class);
                        break;
                    case 1:
                        startActivity(MyWalletActivity.class);
                        break;
                    case 2:
                        startActivity(ParterActivity.class);
                        break;
                    case 3:
                        startActivity(QueryDealActivity.class);
                        break;
                    case 4:
                        startActivity(QueryExchangeActivity.class);
                        break;
                    case 5:
                        startActivity(QueryPosActivity.class);
                        break;
                }
            }
        }
    };
}
