package cn.qijianke.kkt.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.req.ProductTypeParam;
import cn.qijianke.kkt.model.req.SubmitBaodanParam;
import cn.qijianke.kkt.model.resp.ProductTypeResp;
import cn.qijianke.kkt.model.resp.ProductTypeRespWrapper;
import cn.qijianke.kkt.model.resp.SubmitBaodanResp;
import cn.qijianke.kkt.model.resp.UploadImgResp;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.ui.base.BaseActivity;
import cn.qijianke.kkt.utils.RSAUtil;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class FillInBaodanActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.tag_flow)
    TagFlowLayout tag_flow;
    @BindView(R.id.memo_edit)
    EditText memo_edit;
    @BindView(R.id.cut_img_model)
    TagFlowLayout cut_img_model;
    @BindView(R.id.upload_cut_moudle)
    TagFlowLayout upload_cut_moudle;
    @BindView(R.id.exchange_code_edit)
    EditText exchange_code_edit;


   /* String[] strs = {
            "1万", "2万", "3万", "4万", "5万", "6万", "10万", "20万"
    };*/

    List<ProductTypeResp> list = new ArrayList<>();

    ProductTypeResp selectedProduct;

    String[] modelPaths = {"android_asset/cut1.jpg", "android_asset/cut2.jpg"};

    ArrayList<String> models = new ArrayList<>(Arrays.asList(modelPaths));

    ArrayList<String> photos = new ArrayList<>();

    {
        photos.add("");
    }

    ArrayList<String> selectedPhotos = new ArrayList<>();

    String bankCode = "";

    List<String> imgUrls = new ArrayList<>();

    String exchange_code = "";
    String memo;

    @Override
    protected int getContentLayout() {
        return R.layout.fill_in_bao_dan_layout;
    }

    @Override
    protected void view() {
        super.view();
        title.setText("填写报单");
        sub_title.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();
        bankCode = getIntent().getStringExtra("bankCode");
        tag_flow.setAdapter(tagAdapter);
        getImgList();
        cut_img_model.setAdapter(modelAdapter);
        upload_cut_moudle.setAdapter(takephotoAdapter);
        memo_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        memo_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 128) {
                    editable.delete(128, editable.length());
                }
                memo = editable.toString();
            }
        });
        exchange_code_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                /*if (editable.length() > 8) {
                    editable.delete(8, editable.length());
                }*/
                exchange_code = editable.toString();
            }
        });

        getProductType();
    }

    @OnClick(value = {R.id.commit_btn})
    public void handle(View view) {
        switch (view.getId()) {
            case R.id.commit_btn:
                submitBaodan();
                break;
        }
    }

    private void submitBaodan() {
        if (selectedProduct == null) {
            showToast("请选择产品类型");
            return;
        }
        SubmitBaodanParam param = new SubmitBaodanParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        String typeId = selectedProduct.getId();
        String points = selectedProduct.getPoints();


        if (imgUrls.size() == 0) {
            showToast("请上传截图");
            return;
        }

        if (TextUtils.isEmpty(exchange_code)) {
            showToast("请输入兑换码");
            return;
        }

        param.setUserId(userId);
        param.setToken(token);
        param.setBankCode(bankCode);
        param.setTypeId(typeId);
        param.setPoints(points);
        param.setFileName(imgUrls);
        param.setRedeemCode(exchange_code);
        param.setRemark(memo);
        param.setRsaValue(RSAUtil.getRSA(userId + token + bankCode + typeId + points + exchange_code + memo));

        showProgressDialog();
        RequestWrapper.submitBaodan(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveSubmitBaodanResult(SubmitBaodanResp submitBaodanResp) {
        if ("SUCCESS".equals(submitBaodanResp.getResult())) {
            showToast("提交成功");
            finish();
        } else {
            showToast("提交失败");
        }
    }

    private void getProductType() {
        ProductTypeParam param = new ProductTypeParam();
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setBankCode(bankCode);
        param.setRsaValue(RSAUtil.getRSA(userId + token + bankCode));
        showProgressDialog();
        RequestWrapper.getProductType(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveProductTypeResult(ProductTypeRespWrapper productTypeRespWrapper) {
        List<ProductTypeResp> list = productTypeRespWrapper.getList();
        if (list != null) {
            this.list.clear();
            this.list.addAll(list);
            tagAdapter.notifyDataChanged();
        }
    }

    TagAdapter<ProductTypeResp> tagAdapter = new TagAdapter<ProductTypeResp>(list) {

        @Override
        public View getView(FlowLayout parent, int position, ProductTypeResp s) {
            TextView tv = new TextView(tag_flow.getContext());
            tv.setBackgroundResource(R.drawable.baodan_unselect_bg);
            tv.setPadding(20, 5, 20, 5);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tv.setTextColor(Color.WHITE);
            tv.setText(s.getProduct());
            return tv;
        }


        @Override
        public void unSelected(int position, View view) {
            super.unSelected(position, view);
            view.setBackgroundResource(R.drawable.baodan_unselect_bg);
        }

        @Override
        public void onSelected(int position, View view) {
            super.onSelected(position, view);
            view.setBackgroundResource(R.drawable.baodan_select_bg);
            selectedProduct = list.get(position);
        }
    };

    TagAdapter<String> modelAdapter = new TagAdapter<String>(models) {
        @Override
        public View getView(FlowLayout parent, int position, String s) {
            ImageView imageView = new ImageView(cut_img_model.getContext());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            try {
                InputStream is = getAssets().open(s.substring(s.indexOf("/") + 1, s.length()));
                imageView.setImageBitmap(BitmapFactory.decodeStream(is));
                Rect outRect = new Rect();
                BitmapFactory.decodeStream(is, outRect, options);
                int width = (int) (150f / options.outHeight * options.outWidth);
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(width, 150);
                imageView.setLayoutParams(lp);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return imageView;
        }

        @Override
        public void onSelected(int position, View view) {
            super.onSelected(position, view);
            PhotoPreview.builder()
                    .setPhotos(models)
                    .setCurrentItem(position)
                    .setShowDeleteButton(false)
                    .start(FillInBaodanActivity.this);
        }
    };

    private void getImgList() {
        models.clear();
        try {
            String[] imgs = getAssets().list("img/" + bankCode);
            if (imgs != null) {
                for (String s : imgs) {
                    models.add("android_asset/img/" + bankCode + "/" + s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    TagAdapter<String> takephotoAdapter = new TagAdapter<String>(photos) {
        @Override
        public View getView(FlowLayout parent, int position, String s) {
            ImageView imageView = new ImageView(upload_cut_moudle.getContext());
            if (position == 0) {
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(150, 150);
                imageView.setLayoutParams(lp);
                imageView.setImageResource(R.mipmap.camera);
            } else {
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(90, 150);
                imageView.setLayoutParams(lp);
                imageView.setImageBitmap(BitmapFactory.decodeFile(s));
            }
            return imageView;
        }

        @Override
        public void onSelected(int position, View view) {
            super.onSelected(position, view);
            if (position == 0) {
                PhotoPicker.builder()
                        .setPhotoCount(9)
                        .setShowCamera(false)
                        .setShowGif(true)
                        .setPreviewEnabled(true)
                        .start(FillInBaodanActivity.this, PhotoPicker.REQUEST_CODE);
            } else {
                ArrayList<String> temp = new ArrayList<>();
                temp.addAll(photos);
                temp.remove(0);
                PhotoPreview.builder()
                        .setPhotos(temp)
                        .setCurrentItem(position - 1)
                        .setShowDeleteButton(false)
                        .start(FillInBaodanActivity.this);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                selectedPhotos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                for (int i = 0; i < selectedPhotos.size(); i++) {
                    showProgressDialog();
                    RequestWrapper.uploadImg(selectedPhotos.get(i));
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveUploadImgResult(UploadImgResp uploadImgResp) {
        imgUrls.add(uploadImgResp.getFileName());
        this.photos.add(uploadImgResp.getImgPath());
        takephotoAdapter.notifyDataChanged();
    }


}
