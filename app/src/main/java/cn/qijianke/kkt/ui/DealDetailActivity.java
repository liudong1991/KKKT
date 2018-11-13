package cn.qijianke.kkt.ui;

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
import cn.qijianke.kkt.model.event.RequestFinishEvent;
import cn.qijianke.kkt.model.resp.DealDetailResp;
import cn.qijianke.kkt.model.resp.DealDetailRespWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.ui.handlers.RefreshLayoutHandler;

public class DealDetailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.list_v)
    ListView list_v;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    RefreshLayoutHandler handler;
    List<DealDetailResp> list = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.deal_detail_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("交易明细");
        sub_title.setVisibility(View.GONE);
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
        list_v.setAdapter(adapter);
        loadList(1);
    }

    private void loadList(int page) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveLoadListResult(DealDetailRespWrapper dealDetailRespWrapper) {
        handler.loadSuccess(list, dealDetailRespWrapper.getList());
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
        public DealDetailResp getItem(int i) {
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
                view = getLayoutInflater().inflate(R.layout.deal_detail_list_item_layout, null);
                vh = new ViewHolder();
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            return view;
        }

        class ViewHolder {

        }
    };

}
