package cn.qijianke.kkt.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnItemClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.ui.PointsExchangeActivity;
import cn.qijianke.kkt.ui.base.BaseFragment;
import cn.qijianke.kkt.utils.ImageUtil;

public class MainExchangeFragment extends BaseFragment {

    @BindView(R.id.ad_exchange)
    ImageView ad_exchange;
    @BindView(R.id.exchange_grid)
    GridView exchange_grid;

    Integer[] imgs = {
            R.mipmap.gongshang, R.mipmap.pingan,
            R.mipmap.zhongguo, R.mipmap.zhaoshang,
            R.mipmap.jianshe, R.mipmap.beijing,
            R.mipmap.pufa, R.mipmap.jiaotong,
            R.mipmap.huifeng, R.mipmap.youzheng,
            R.mipmap.guangda
    };

    String[] names = {
            "工商银行", "平安银行", "中国银行", "招商银行", "建设银行", "北京银行", "浦发银行", "交通银行", "汇丰银行", "邮政储蓄", "光大银行"
    };

    String[] codes = {
            "ICBC", "PAB", "BOC", "CMB", "CCB", "BJB", "SPD", "BOCOM", "HSBC", "PSBC", "CEB"
    };

    String[] memos = {
            "拨打工商银行信用卡电话：95588，转人工服务，向工商银行信用卡中心的客服提出查询积分的要求即可。",
            "拨打平安银行信用卡客服电话：95511转2，转人工服务，要求客服专员为您查询平安银行信用卡积分情况",
            "使用银行预留的手机号码，编辑短信“17#卡号后四位”发送到号码95566",
            "使用银行预留的手机号码，联通、电信用户发送#JF到95555，移动用户发送#JF到1065795555",
            "使用银行预留的手机号码，编辑短信“CXJF”发到95533，查询积分",
            "主卡持卡人可致电北京银行信用卡中心客户服务热线400-660-1169查询即时累计积分",
            "使用银行预留的手机号码，编辑短信“JFCX+空格+卡号后四位”发送至95528（+号不用填写）",
            "使用银行预留的手机号码，编辑短信“cc积分#卡号后四位”发送至95559",
            "使用银行预留手机号拨打汇丰中国信用卡24小时客服热线4008695366，转人工服务查询。",
            "拨打邮储银行信用卡服务热线4008895580，选择人工服务或通过语音菜单自助查询。",
            "使用银行预留的手机号码，发送“积分”至95595查询积分余额"
    };

    @Override
    protected int getContentLayout() {
        return R.layout.exchange_layout;
    }

    @Override
    protected void init() {
        super.init();
        exchange_grid.setAdapter(adapter);
    }

    @OnItemClick(value = {R.id.exchange_grid})
    public void handleItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle bundle = new Bundle();
        bundle.putString("bankCode", codes[i]);
        bundle.putString("bankName", names[i]);
        bundle.putString("memo", memos[i]);
        startActivity(PointsExchangeActivity.class, bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageUtil.adjustImg(ad_exchange, R.mipmap.exchange_banner);
    }

    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public Integer getItem(int i) {
            return imgs[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LinearLayout ll = new LinearLayout(getContext());
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setGravity(Gravity.CENTER);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(getItem(i));
            TextView tv = new TextView(getContext());
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tv.setText(names[i]);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(96, 96);
            ll.addView(imageView, lp);

            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = 20;

            ll.addView(tv, lp);

            return ll;
        }
    };

}
