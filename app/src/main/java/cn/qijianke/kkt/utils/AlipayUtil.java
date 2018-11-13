package cn.qijianke.kkt.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import cn.qijianke.kkt.model.event.PaySuccessEvent;

public class AlipayUtil {

    public static void pay(final Activity activity, final String orderInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask(activity);
                final Map<String, String> result = payTask.payV2(orderInfo, true);
                Log.i(getClass().getName(), "wust==>alipay result:" + result);
                if ("9000".equals(result.get("resultStatus"))) {
                    EventBus.getDefault().post(new PaySuccessEvent());
                    if (TextUtils.isEmpty(result.get("memo"))) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    if (TextUtils.isEmpty(result.get("memo"))) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, result.get("memo"), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();

    }

}
