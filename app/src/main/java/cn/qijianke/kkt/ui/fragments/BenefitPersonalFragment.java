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
import cn.qijianke.kkt.model.req.ProfitParam;
import cn.qijianke.kkt.model.resp.PersonalProfitWrapper;
import cn.qijianke.kkt.model.resp.ProfitResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseFragment;
import cn.qijianke.kkt.ui.handlers.RefreshLayoutHandler;
import cn.qijianke.kkt.utils.RSAUtil;

public class BenefitPersonalFragment extends BaseFragment {

    @BindView(R.id.benefit_detail_list)
    ListView benefit_detail_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    RefreshLayoutHandler handler;
    List<ProfitResp> list = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.benefit_detail_list_layout;
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
        benefit_detail_list.setAdapter(adapter);

        showProgressDialog();
        loadList(1);
    }


    private void loadList(int page) {
        ProfitParam param = new ProfitParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setPageId(page);
        param.setRsaValue(RSAUtil.getRSA(userId + token + page));
        RequestWrapper.getPersonalProfit(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveLoadListResult(PersonalProfitWrapper personalProfitWrapper) {
        List<ProfitResp> profitRespList = personalProfitWrapper.getList();
        handler.loadSuccess(list, profitRespList);
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
        public ProfitResp getItem(int i) {
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
                view = getLayoutInflater().inflate(R.layout.benefit_detail_item_layout, null);
                vh = new ViewHolder();
                vh.date_tv = view.findViewById(R.id.date_tv);
                vh.benefit_tv = view.findViewById(R.id.benefit_tv);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            vh.date_tv.setText(getItem(i).getTradeTime());
            vh.benefit_tv.setText(String.format("%1$.2f元", getItem(i).getProfitAmt() / 100d));

            return view;
        }

        class ViewHolder {
            TextView date_tv;
            TextView benefit_tv;
        }
    };
}
