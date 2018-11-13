package cn.qijianke.kkt.net;

import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import cn.qijianke.kkt.KKTApplication;
import cn.qijianke.kkt.model.event.LoginExpireEvent;
import cn.qijianke.kkt.model.event.RequestFinishEvent;
import cn.qijianke.kkt.model.resp.RespDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallbackWrapper<T> implements Callback {

    Callback<RespDto<T>> callback = new Callback<RespDto<T>>() {
        @Override
        public void onResponse(Call<RespDto<T>> call, Response<RespDto<T>> response) {
            RespDto<T> body = response.body();
            if (body == null) return;

            if ("000000".equals(body.getCode())) {
                onSuccess(body.getObject());
            } else if ("000005".equals(body.getCode())) {
                EventBus.getDefault().post(new LoginExpireEvent());
                //onFail("你的登录已失效，请重新登录");
            } else {
                onFail(body.getMessage());
            }
        }

        @Override
        public void onFailure(Call<RespDto<T>> call, Throwable t) {
            Toast.makeText(KKTApplication.getInstance(), "请求失败", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onResponse(Call call, Response response) {
        EventBus.getDefault().post(new RequestFinishEvent());
        callback.onResponse(call, response);
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        EventBus.getDefault().post(new RequestFinishEvent());
        callback.onFailure(call, t);
    }

    protected abstract void onSuccess(T t);

    protected abstract void onFail(String errorMsg);
}
