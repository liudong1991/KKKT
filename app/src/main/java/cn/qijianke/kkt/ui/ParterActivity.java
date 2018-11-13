package cn.qijianke.kkt.ui;

import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.event.RequestFinishEvent;
import cn.qijianke.kkt.model.req.ParternerParam;
import cn.qijianke.kkt.model.resp.ParterResp;
import cn.qijianke.kkt.model.resp.ParterRespWrapper;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.ui.handlers.RefreshLayoutHandler;
import cn.qijianke.kkt.utils.RSAUtil;

public class ParterActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.parter_list)
    ListView parter_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.empty_tv)
    TextView empty_tv;
    @BindView(R.id.tips)
    TextView tips;

    RefreshLayoutHandler handler;
    List<ParterResp> list = new ArrayList<>();

    String mobilephone = "";

    @Override
    protected int getContentLayout() {
        return R.layout.parter_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("合伙人");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();

        parter_list.setEmptyView(empty_tv);

        search_edit.addTextChangedListener(new TextWatcher() {

            CharSequence old;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                old = charSequence;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (!Pattern.compile("[0-9]*").matcher(str).matches()) {
                    editable.delete(0, editable.length());
                    editable.append(old);
                }
                mobilephone = editable.toString();
            }
        });

        handler = RefreshLayoutHandler.getHandler(refreshLayout, new RefreshLayoutHandler.OnLoadListListener() {
            @Override
            public void loadListByPage(int page) {
                loadList(page);
            }
        });
        parter_list.setAdapter(adapter);

        showProgressDialog();
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

    @OnItemClick(value = {R.id.parter_list})
    public void handleItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // startActivity(GroupMemberActivity.class);
    }

    private void loadList(int page) {
        ParternerParam param = new ParternerParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();

        param.setUserId(userId);
        param.setToken(token);
        param.setMobileNum(mobilephone);
        param.setPageId(page);
        param.setRsaValue(RSAUtil.getRSA(userId + token + page));
        RequestWrapper.getPaterners(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveLoadListResult(ParterRespWrapper parterRespWrapper) {
        handler.loadSuccess(list, parterRespWrapper.getList());
        adapter.notifyDataSetChanged();
        tips.setVisibility(list.size() > 0 ? View.VISIBLE : View.GONE);
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
        public ParterResp getItem(int i) {
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
                view = getLayoutInflater().inflate(R.layout.parter_list_item_layout, null);
                vh = new ViewHolder();
                vh.name = view.findViewById(R.id.name);
                vh.mobilephone = view.findViewById(R.id.phone);
                vh.teamNum = view.findViewById(R.id.group_num);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            final String mobileNum = getItem(i).getMobileNum();
            vh.name.setText(getItem(i).getUserName());
            vh.mobilephone.setText("电话：" + mobileNum);
            vh.teamNum.setText("团队人数：" + getItem(i).getCustomerId());

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
            TextView name;
            TextView mobilephone;
            TextView teamNum;
        }
    };
}
