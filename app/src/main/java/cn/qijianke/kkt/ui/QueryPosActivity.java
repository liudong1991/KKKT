package cn.qijianke.kkt.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.event.RequestFinishEvent;
import cn.qijianke.kkt.model.req.PosParam;
import cn.qijianke.kkt.model.req.PosTotalParam;
import cn.qijianke.kkt.model.resp.PosTotalResp;
import cn.qijianke.kkt.model.resp.QueryPosResp;
import cn.qijianke.kkt.model.resp.QueryPosRespWrapper;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.ui.handlers.RefreshLayoutHandler;
import cn.qijianke.kkt.utils.RSAUtil;

public class QueryPosActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.list_container)
    ListView list_container;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.pos_total)
    TextView pos_total;
    @BindView(R.id.tips)
    TextView tips;

    RefreshLayoutHandler handler;
    List<QueryPosResp> list = new ArrayList<>();

    String mobileNum = "";

    @Override
    protected int getContentLayout() {
        return R.layout.query_pos_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("机具查询");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();
        search_edit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mobileNum = editable.toString();
            }
        });
        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        handler = RefreshLayoutHandler.getHandler(refreshLayout, new RefreshLayoutHandler.OnLoadListListener() {
            @Override
            public void loadListByPage(int page) {
                loadList(page);
            }
        });
        list_container.setAdapter(adapter);

        getPosTotalAmount();
        loadList(1);
    }

    @OnClick(value = {R.id.search_btn})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.search_btn:
                loadList(handler.prePageInit());
                break;
        }
    }

    private void getPosTotalAmount() {
        PosTotalParam param = new PosTotalParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setRsaValue(RSAUtil.getRSA(userId + token));
        showProgressDialog();
        RequestWrapper.getPosTotalAmount(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receivePosTotalAmountResult(PosTotalResp posTotalResp) {
        if (!TextUtils.isEmpty(posTotalResp.getSumpos()))
            pos_total.setText(posTotalResp.getSumpos() + "个");
    }

    private void loadList(int page) {
        PosParam param = new PosParam();

        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setPageId(page);
        param.setMobileNum(mobileNum);
        param.setRsaValue(RSAUtil.getRSA(userId + token + page));
        RequestWrapper.getPos(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveLoadListResult(QueryPosRespWrapper queryPosRespWrapper) {
        handler.loadSuccess(list, queryPosRespWrapper.getList());
        tips.setVisibility(list.size() > 0 ? View.VISIBLE : View.GONE);
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
        public QueryPosResp getItem(int i) {
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
                view = getLayoutInflater().inflate(R.layout.query_pos_list_item_layout, null);
                vh = new ViewHolder();
                vh.posSn = view.findViewById(R.id.posSn);
                vh.merchant = view.findViewById(R.id.merchant);
                vh.recommand = view.findViewById(R.id.recommand);
                vh.active = view.findViewById(R.id.active);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            final String mobileNum = getItem(i).getMobileNum();

            vh.posSn.setText(getItem(i).getPhySn());
            vh.merchant.setText(getItem(i).getUserName());
            vh.recommand.setText(mobileNum);
            vh.active.setText(getItem(i).getActivatedAt() == null ? "未激活" : getItem(i).getActivatedAt());
            vh.active.setTextColor(getItem(i).getActivatedAt() == null ? Color.parseColor("#B12525") : Color.parseColor("#25B14C"));

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + mobileNum));
                    startActivity(intent);
                    return true;
                }
            });

            return view;
        }

        class ViewHolder {
            TextView posSn;
            TextView merchant;
            TextView recommand;
            TextView active;
        }
    };
}
