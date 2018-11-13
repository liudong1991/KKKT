package cn.qijianke.kkt.ui.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.event.RequestFinishEvent;
import cn.qijianke.kkt.model.req.DealRecordParam;
import cn.qijianke.kkt.model.resp.DealQueryResp;
import cn.qijianke.kkt.model.resp.GroupDealQueryRespWrapper;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseFragment;
import cn.qijianke.kkt.ui.handlers.RefreshLayoutHandler;
import cn.qijianke.kkt.utils.RSAUtil;

public class DealQueryGroupFragment extends BaseFragment {

    @BindView(R.id.deal_query_list)
    ListView deal_query_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    RefreshLayoutHandler handler;
    List<DealQueryResp> list = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.query_deal_list_layout;
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
        deal_query_list.setAdapter(adapter);

        loadList(1);
    }

    @OnItemClick(value = {R.id.deal_query_list})
    public void handleItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //startActivity(DealDetailActivity.class);
    }

    private void loadList(int page) {
        DealRecordParam param = new DealRecordParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setPageId(page);
        param.setRsaValue(RSAUtil.getRSA(userId + token + page));
        RequestWrapper.getGroupDealRecord(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveLoadListResult(GroupDealQueryRespWrapper groupDealQueryRespWrapper) {
        handler.loadSuccess(list, groupDealQueryRespWrapper.getList());
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
        public DealQueryResp getItem(int i) {
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
                view = getLayoutInflater().inflate(R.layout.query_deal_list_item_layout, null);
                vh = new ViewHolder();
                vh.date_tv = view.findViewById(R.id.date_tv);
                vh.money_tv = view.findViewById(R.id.money_tv);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            vh.date_tv.setText(getItem(i).getTradeTime());
            vh.money_tv.setText(String.format("%1$.2f元", Double.parseDouble(getItem(i).getTradeAmt()) / 100d));


            return view;
        }

        class ViewHolder {
            TextView date_tv;
            TextView money_tv;
        }
    };
}
