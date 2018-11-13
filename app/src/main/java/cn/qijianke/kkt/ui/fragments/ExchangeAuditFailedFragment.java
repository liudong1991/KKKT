package cn.qijianke.kkt.ui.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.event.RequestFinishEvent;
import cn.qijianke.kkt.model.event.SearchExchangeEvent;
import cn.qijianke.kkt.model.req.SearchExchangeParam;
import cn.qijianke.kkt.model.resp.SearchExchangeResp;
import cn.qijianke.kkt.model.resp.SearchExchangeRespWrapper;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.QueryExchangeActivity;
import cn.qijianke.kkt.ui.base.BaseFragment;
import cn.qijianke.kkt.ui.handlers.RefreshLayoutHandler;
import cn.qijianke.kkt.utils.RSAUtil;

public class ExchangeAuditFailedFragment extends BaseFragment {

    @BindView(R.id.query_exchange_list)
    ListView query_exchange_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    RefreshLayoutHandler handler;
    List<SearchExchangeResp> list = new ArrayList<>();


    @Override
    protected int getContentLayout() {
        return R.layout.query_exchange_list_layout;
    }

    @Override
    protected void init() {
        super.init();
        handler = RefreshLayoutHandler.getHandler(refreshLayout, new RefreshLayoutHandler.OnLoadListListener() {
            @Override
            public void loadListByPage(int page) {
                loadList(page);
            }
        });
        query_exchange_list.setAdapter(adapter);
        loadList(1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void search(SearchExchangeEvent event) {
        showProgressDialog();
        loadList(handler.prePageInit());
    }

    private void loadList(int page) {
        SearchExchangeParam param = new SearchExchangeParam();
        param.setTag("4");
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        String mobileNum = ((QueryExchangeActivity) getActivity()).getSearchContent();
        String pos = "REJT";
        param.setUserId(userId);
        param.setToken(token);
        param.setMobileNum(mobileNum);
        param.setPos(pos);
        param.setPageId(page);
        param.setRsaValue(RSAUtil.getRSA(userId + token + page));
        RequestWrapper.searchExchange(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveLoadListResult(SearchExchangeRespWrapper searchExchangeRespWrapper) {
        if (!"4".equals(searchExchangeRespWrapper.getTag())) {
            return;
        }
        handler.loadSuccess(list, searchExchangeRespWrapper.getList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void requestFinish(RequestFinishEvent ev) {
        super.requestFinish(ev);
        handler.loadFinish();
    }

    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public SearchExchangeResp getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.query_exchange_list_item_layout, null);
                vh = new ViewHolder();
                vh.bank_name = view.findViewById(R.id.bank_name);
                vh.order_no = view.findViewById(R.id.order_no);
                vh.points_tv = view.findViewById(R.id.points_tv);
                vh.exchange_amount_tv = view.findViewById(R.id.exchange_amount_tv);
                vh.time_tv = view.findViewById(R.id.time_tv);
                vh.status_tv = view.findViewById(R.id.status_tv);
                vh.remark_tv = view.findViewById(R.id.remark_tv);
                vh.opinion_tv = view.findViewById(R.id.opinion_tv);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            vh.bank_name.setText(getItem(i).getExchangeTicket());
            vh.order_no.setText("单号：" + getItem(i).getId());
            vh.points_tv.setText(getItem(i).getPoints());
            vh.exchange_amount_tv.setText(String.format("%1$.2f", getItem(i).getAmount()));
            vh.time_tv.setText(getItem(i).getCreatedAt());
            String status = getItem(i).getStatus();
            vh.status_tv.setText("APPR".equals(status) ? "审核通过" : ("DRFT".equals(status) ? "审核中" : "审核失败"));
            vh.remark_tv.setVisibility(View.VISIBLE);
            vh.remark_tv.setText(getItem(i).getRemark());
            vh.opinion_tv.setVisibility(View.VISIBLE);
            vh.opinion_tv.setText(getItem(i).getOpinion());
            return view;
        }

        class ViewHolder {
            TextView bank_name;
            TextView order_no;
            TextView points_tv;
            TextView exchange_amount_tv;
            TextView time_tv;
            TextView status_tv;
            TextView remark_tv;
            TextView opinion_tv;
        }
    };
}
