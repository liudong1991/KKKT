package cn.qijianke.kkt.ui;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.event.CalculatePosTotalEvent;
import cn.qijianke.kkt.model.event.RequestFinishEvent;
import cn.qijianke.kkt.model.req.PosInfoParam;
import cn.qijianke.kkt.model.req.PosOrderNoParam;
import cn.qijianke.kkt.model.resp.PosInfoResp;
import cn.qijianke.kkt.model.resp.PosInfoRespWrapper;
import cn.qijianke.kkt.model.resp.PosOrderNoResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.ui.handlers.RefreshLayoutHandler;
import cn.qijianke.kkt.ui.view.CountView;
import cn.qijianke.kkt.ui.view.ImageViewWrapper;
import cn.qijianke.kkt.utils.AlipayUtil;
import cn.qijianke.kkt.utils.RSAUtil;

public class ApplyPosActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.list_v)
    ListView list_v;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.total_v)
    TextView total_v;

    List<PosInfoResp> list = new ArrayList<>();

    RefreshLayoutHandler handler;

    int selectedIndex = -1;

    @Override
    protected int getContentLayout() {
        return R.layout.apply_pos_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("机具申请");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();
        handler = RefreshLayoutHandler.getHandler(refreshLayout, new RefreshLayoutHandler.OnLoadListListener() {
            @Override
            public void loadListByPage(int page) {
                loadPosInfoList(page);
            }
        });
        list_v.setAdapter(adapter);

        showProgressDialog();
        loadPosInfoList(1);
    }

    @OnClick(value = {R.id.pay})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.pay:
                getPosOrderInfo();
                break;
        }
    }

    private void loadPosInfoList(int page) {
        PosInfoParam param = new PosInfoParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setPageId(page);
        param.setRsaValue(RSAUtil.getRSA(userId + token + page));
        RequestWrapper.loadPosInfoList(param);
    }

    private void getPosOrderInfo() {
        if (selectedIndex == -1) {
            showToast("请选择pos机");
            return;
        }
        int num = list.get(selectedIndex).getNum();
        if (num == 0) {
            showToast("请选择pos机数量");
            return;
        }

        PosOrderNoParam param = new PosOrderNoParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        String posId = list.get(selectedIndex).getId();
        String quantity = num + "";
        String posCategory = list.get(selectedIndex).getPosCategory();
        param.setUserId(userId);
        param.setToken(token);
        param.setPosId(posId);
        param.setQuantity(quantity);
        param.setPosCategory(posCategory);
        param.setRsaValue(RSAUtil.getRSA(userId + token + posId + quantity + posCategory));
        showProgressDialog();
        RequestWrapper.getPosOrderNo(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receivePosInfoListResult(PosInfoRespWrapper posInfoRespWrapper) {
        List<PosInfoResp> posInfoRespList = posInfoRespWrapper.getList();
        handler.loadSuccess(list, posInfoRespList);
        adapter.notifyDataSetChanged();
        EventBus.getDefault().post(new CalculatePosTotalEvent(CalculateTotal()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receivePosOrderNoResult(PosOrderNoResp posOrderNoResp) {
        if (!TextUtils.isEmpty(posOrderNoResp.getOrderInfo()))
            AlipayUtil.pay(this, posOrderNoResp.getOrderInfo());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void requestFinish(RequestFinishEvent ev) {
        super.requestFinish(ev);
        handler.loadFinish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void total(CalculatePosTotalEvent ev) {
        total_v.setText(String.format("￥%1$.2f", ev.getTotal()));
    }

    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public PosInfoResp getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            ViewHolder vh;
            if (view == null) {
                vh = new ViewHolder();
                view = getLayoutInflater().inflate(R.layout.apply_pos_item_layout, null);
                vh.imgW = view.findViewById(R.id.checked_tag);
                vh.category = view.findViewById(R.id.category);
                vh.price = view.findViewById(R.id.price);
                vh.model = view.findViewById(R.id.model);
                vh.count_v = view.findViewById(R.id.count_v);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            vh.category.setText(getItem(i).getPosCategory());
            vh.price.setText("￥：" + getItem(i).getUnitPrice());
            vh.model.setText("型号：" + getItem(i).getLocalPosType());
            vh.imgW.check(getItem(i).isChecked());
            vh.count_v.setCountNum(getItem(i).getNum());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectPos(i);
                }
            });

            vh.count_v.setOnCountNumChangeListener(new CountView.OnCountNumChangeListener() {
                @Override
                public void afterChanged(int countNum) {
                    getItem(i).setNum(countNum);
                    EventBus.getDefault().post(new CalculatePosTotalEvent(CalculateTotal()));
                }
            });
            return view;
        }

        class ViewHolder {
            ImageViewWrapper imgW;
            TextView category;
            TextView price;
            TextView model;
            CountView count_v;
        }
    };

    private void selectPos(int index) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(i == index);
        }
        selectedIndex = index;
        adapter.notifyDataSetChanged();
        EventBus.getDefault().post(new CalculatePosTotalEvent(CalculateTotal()));
    }

    private double CalculateTotal() {
        double total = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChecked())
                total += list.get(i).getNum() * Double.parseDouble(list.get(i).getUnitPrice());
        }
        return total;
    }
}
