package cn.qijianke.kkt.ui.handlers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.qijianke.kkt.KKTApplication;
import cn.qijianke.kkt.R;
import cn.qijianke.kkt.common.Constants;
import cn.qijianke.kkt.model.resp.UpgradeResp;
import cn.qijianke.kkt.utils.SystemUtil;
import cn.qijianke.kkt.utils.TextUtil;

public class UpdateHandler {

    public static void update(Activity activity, UpgradeResp upgradeResp) {
        judge(activity, upgradeResp);
    }

    private static boolean flag;
    private static boolean isLoading;

    private static void loadApk(final Activity activity, final UpgradeResp upgradeResp, final boolean isForce) {
        final Handler handler = new Handler(activity.getMainLooper()) {
            ProgressDialog dialog;

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        if (dialog == null) {
                            dialog = new ProgressDialog(activity, R.style.dialog_custom);
                        }
                        break;
                    case 2:
                        if (dialog != null) {
                            dialog.setProgress(msg.arg1, msg.arg2);
                        }
                        break;
                    case 3:
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        break;
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                int length;
                int current = 0;
                flag = true;
                isLoading = false;
                handler.sendEmptyMessage(1);
                String error = null;
                try {
                    File temp = getTempApkPath("kkt_" + upgradeResp.getVersion() + ".wcl.tmp");
                    if (temp.exists()) {
                        current = (int) temp.length();
                    }
                    String loadUrl = upgradeResp.getAndroidUrl();
                    URL url = new URL(loadUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(20000);
                    connection.setReadTimeout(20000);
                    connection.setRequestProperty("Range", "bytes=" + current + "-");
                    int responseCode = connection.getResponseCode();
                    isLoading = true;
                    if (responseCode == 200 || responseCode == 206) {
                        InputStream in = connection.getInputStream();
                        length = connection.getContentLength() + current;
                        FileOutputStream fos = new FileOutputStream(temp, true);
                        byte[] b = new byte[1024];
                        int len;
                        while (flag && (len = in.read(b)) != -1) {
                            current += len;
                            send(handler, length, current);
                            fos.write(b, 0, len);
                        }
                        fos.close();
                        in.close();

                        if (flag) {
                            final File file = getApkPath(upgradeResp.getVersion());
                            temp.renameTo(file);
                            openApk(file);
                            temp.delete();
                        }
                    } else {
                        error = "找不到资源或服务器异常";
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                    error = "当前网络连接断开或连接超时，请检查网络";
                } finally {
                    final String err = error;
                    isLoading = false;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(3);
                            showUpdate(activity, upgradeResp, isForce);
                            if (!TextUtils.isEmpty(err)) {
                                Toast.makeText(activity, err, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }).start();
    }

    private static File getApkPath(String version) {
        String name = "kkt_" + version + ".apk";
        String apkPath = Environment.getExternalStorageDirectory() + File.separator + KKTApplication.getInstance().getApplicationInfo().packageName + File.separator + "update" + File.separator + name;
        File file = new File(apkPath);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        return new File(apkPath);
    }

    private static File getTempApkPath(String tempName) {
        String apkPath = Environment.getExternalStorageDirectory() + File.separator + KKTApplication.getInstance().getApplicationInfo().packageName + File.separator + "update" + File.separator + tempName;
        File file = new File(apkPath);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        return new File(apkPath);
    }

    private static void send(Handler handler, final int length, final int current) {
        Message message = handler.obtainMessage();
        message.what = 2;
        message.arg1 = current;
        message.arg2 = length;
        handler.sendMessage(message);
    }

    private static void openApk(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        KKTApplication.getInstance().startActivity(intent);
    }

    private static void showUpdate(final Activity activity, final UpgradeResp upgradeResp, final boolean isForce) {
        final Dialog dialog = new Dialog(activity, R.style.dialog_custom);
        dialog.setContentView(R.layout.apk_update_layout);
        dialog.show();
        dialog.setCancelable(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0);
            }
        });

        Window window = dialog.getWindow();
        assert window != null;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.y = -50;
        window.setAttributes(lp);

        TextView cancel = dialog.findViewById(R.id.updae_cancel);
        TextView confirm = dialog.findViewById(R.id.update_confirm);
        TextView content = dialog.findViewById(R.id.update_detail);

        cancel.setVisibility(isForce ? View.GONE : View.VISIBLE);
        content.setText(upgradeResp.getRemark());

        View.OnClickListener listener = new View.OnClickListener() {

            void show() {
                dialog.dismiss();

                final Dialog dialog1 = new Dialog(activity, R.style.dialog_custom);
                dialog1.setContentView(R.layout.notice_dialog_layout);
                dialog1.show();
                dialog1.setCancelable(false);
                dialog1.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        return (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0);
                    }
                });

                Window window = dialog1.getWindow();
                assert window != null;
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.gravity = Gravity.CENTER;
                lp.y = -50;
                window.setAttributes(lp);

                View.OnClickListener listener1 = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.yes:
                                loadApk(activity, upgradeResp, isForce);
                                break;
                            case R.id.no:
                                dialog.show();
                                break;
                        }
                        dialog1.dismiss();
                    }
                };
                dialog1.findViewById(R.id.yes).setOnClickListener(listener1);
                dialog1.findViewById(R.id.no).setOnClickListener(listener1);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.updae_cancel:
                        dialog.dismiss();
                        break;
                    case R.id.update_confirm:
                        File file = getApkPath(upgradeResp.getVersion());
                        if (file.exists()) {
                            openApk(file);
                        } else {
                            int type = SystemUtil.GetNetype(KKTApplication.getInstance());
                            if (type == -1) {
                                Toast.makeText(activity, "网络未链接", Toast.LENGTH_SHORT).show();
                            } else if (type == 1) {
                                dialog.dismiss();
                                loadApk(activity, upgradeResp, isForce);
                            } else {
                                show();
                            }
                        }
                        break;
                }
            }
        };
        cancel.setOnClickListener(listener);
        confirm.setOnClickListener(listener);
    }

    private static void judge(final Activity activity, final UpgradeResp upgradeResp) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ("O".equals(upgradeResp.getIsUpdate())) {
                    showUpdate(activity, upgradeResp, false);
                } else if ("M".equals(upgradeResp.getIsUpdate())) {
                    showUpdate(activity, upgradeResp, true);
                }
            }
        });

    }

    private static class ProgressDialog extends Dialog {
        private ProgressBar bar;
        private TextView cur;
        private TextView total;
        private TextView rate;
        private Dialog dialog1;

        ProgressDialog(@NonNull Context context, @StyleRes int themeResId) {
            super(context, themeResId);
            setContentView(R.layout.update_progress_layout);
            bar = findViewById(R.id.progress);
            cur = findViewById(R.id.curTxt);
            total = findViewById(R.id.totalTxt);
            rate = findViewById(R.id.progress_rate);
            show();
            setCancelable(false);
            setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                        if (isLoading)
                            if (dialog1 == null) {
                                dialog1 = new Dialog(getContext(), R.style.dialog_custom);
                                dialog1.setContentView(R.layout.notice_dialog_layout);
                                dialog1.show();
                                ((TextView) dialog1.findViewById(R.id.content)).setText("是否取消下载？");
                                dialog1.setCancelable(false);

                                Window window = dialog1.getWindow();
                                assert window != null;
                                WindowManager.LayoutParams lp = window.getAttributes();
                                lp.gravity = Gravity.CENTER;
                                lp.width = Constants.SCREEN_WIDTH * 3 / 4;
                                lp.y = -50;
                                window.setAttributes(lp);

                                View.OnClickListener listener1 = new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        switch (v.getId()) {
                                            case R.id.yes:
                                                flag = false;
                                                break;

                                        }
                                        dialog1.dismiss();
                                    }
                                };
                                dialog1.findViewById(R.id.yes).setOnClickListener(listener1);
                                dialog1.findViewById(R.id.no).setOnClickListener(listener1);
                            } else {
                                dialog1.show();
                            }
                        return true;
                    }
                    return false;
                }
            });

            Window window = getWindow();
            assert window != null;
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = Constants.SCREEN_WIDTH * 13 / 16;
            lp.gravity = Gravity.CENTER;
            lp.y = -50;
            window.setAttributes(lp);
        }

        @Override
        public void dismiss() {
            super.dismiss();
            if (dialog1 != null) {
                dialog1.dismiss();
            }
        }

        @SuppressLint("SetTextI18n")
        void setProgress(int cur, int total) {
            bar.setMax(total);
            bar.setProgress(cur);
            this.cur.setText(TextUtil.numFormat(cur / (1024 * 1024f) + "") + "M");
            this.total.setText("/" + TextUtil.numFormat(total / (1024 * 1024f) + "") + "M");
            String rate = (int) (cur / (float) total * 100) + "%";
            if (!"100%".equals(rate)) {
                SpannableString ss = new SpannableString(rate);
                ss.setSpan(new AbsoluteSizeSpan(16, true), rate.length() - 1, rate.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                this.rate.setText(ss);
            }
        }

    }


}
