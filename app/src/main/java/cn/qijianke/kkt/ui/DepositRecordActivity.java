package cn.qijianke.kkt.ui;

import android.text.TextUtils;
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
import cn.qijianke.kkt.model.req.DepositRecordParam;
import cn.qijianke.kkt.model.resp.DepositRecordResp;
import cn.qijianke.kkt.model.resp.DepositRecordRespWrapper;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.ui.handlers.RefreshLayoutHandler;
import cn.qijianke.kkt.utils.RSAUtil;

public class DepositRecordActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.list_v)
    ListView list_v;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    RefreshLayoutHandler handler;
    List<DepositRecordResp> list = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.deposit_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("提现记录");
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
        DepositRecordParam param = new DepositRecordParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setPageId(page);
        param.setRsaValue(RSAUtil.getRSA(userId + token + page));
        showProgressDialog();
        RequestWrapper.depositRecord(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveLoadListResult(DepositRecordRespWrapper depositRecordRespWrapper) {
        handler.loadSuccess(list, depositRecordRespWrapper.getList());
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
        public DepositRecordResp getItem(int i) {
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
                view = getLayoutInflater().inflate(R.layout.deposit_record_list_item_layout, null);
                vh = new ViewHolder();
                vh.date_tv = view.findViewById(R.id.date_tv);
                vh.deposit_record_tv = view.findViewById(R.id.deposit_record_tv);
                vh.deposit_record_status = view.findViewById(R.id.deposit_record_status);
                vh.remark_tv = view.findViewById(R.id.remark_tv);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            vh.date_tv.setText(getItem(i).getCreatedAt());
            vh.deposit_record_tv.setText(String.format("%1$.2f元", getItem(i).getAmount() / 100d));
//            DRFT 审核中
//            PASS 处理中
//            DONE 已完成
//            REJT 已拒绝
            String status = getItem(i).getStatus();
            vh.deposit_record_status.setText("DRFT".equals(status) ? "审核中" : ("PASS".equals(status) ? "处理中" : ("DONE".equals(status) ? "已完成" : ("REJT".equals(status) ? "已拒绝" : "未知"))));
            String remark = getItem(i).getRemark();
            vh.remark_tv.setVisibility(TextUtils.isEmpty(remark) ? View.GONE : View.VISIBLE);
            vh.remark_tv.setText(getItem(i).getRemark());
            return view;
        }

        class ViewHolder {
            TextView date_tv;
            TextView deposit_record_tv;
            TextView deposit_record_status;
            TextView remark_tv;
        }
    };
}
