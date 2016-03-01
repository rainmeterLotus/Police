package com.police.momo.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.police.momo.AppManager;
import com.police.momo.MainApplication;
import com.police.momo.R;
import com.police.momo.util.MLog;
import com.police.momo.util.MessageWhat;
import com.police.momo.util.SharedPreferencesUtils;
import com.police.momo.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by momo on 2015/9/12.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener, Handler.Callback {
    protected final String TAG = getClass().getSimpleName();
    protected int diaplayWidth;
    protected int diaplayHeight;
    protected Context mContext;
    private ProgressDialog progressDialog;
    protected ActionBar actionBar;
    protected MainApplication application;
    protected Handler handler;
    protected View emptyProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MLog.i(TAG, "onCreate");
        AppManager.getAppManager().addActivity(this);
        mContext = this;
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        WindowManager mWindowManager = getWindowManager();
        Display display = mWindowManager.getDefaultDisplay();
        diaplayWidth = display.getWidth();
        diaplayHeight = display.getHeight();
        MLog.i(TAG, "diaplayWidth" + diaplayWidth + "====" + "diaplayHeight" + diaplayHeight);
        if (null == application) {
            application = (MainApplication) this.getApplication();
        }
        if (null == handler) {
            handler = new Handler(this);
            application.setCurrentHandler(handler);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    protected View getEmptyProgressView() {
        if (emptyProgressView == null) {
            emptyProgressView = findViewById(R.id.hg_progressContainer);
        }
        return emptyProgressView;
    }

    protected void beginEmptyProgress(String showText) {
        View contentProgressView = getEmptyProgressView();
        if (contentProgressView == null) {
            return;
        }
        contentProgressView.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        ((TextView) contentProgressView.findViewById(R.id.pb_textView)).setText(showText);
        contentProgressView.setVisibility(View.VISIBLE);
    }

    protected void stopEmptyProgress(String showText) {
        View contentProgressView = getEmptyProgressView();
        if (contentProgressView == null) {
            return;
        }
        if (TextUtils.isEmpty(showText)) {
            contentProgressView.setVisibility(View.GONE);
        } else {
            contentProgressView.findViewById(R.id.progressBar).setVisibility(View.GONE);
            ((TextView) contentProgressView.findViewById(R.id.pb_textView)).setText(showText);
            contentProgressView.setVisibility(View.VISIBLE);
        }
    }

    protected Activity getActivity() {
        return this;
    }

    /*
     * (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     * 点击回调
     */
    @Override
    public void onClick(View v) {

    }

    public void openProgressDialog(final String message) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(mContext);
        }
        progressDialog.setMessage(message);
        if (!isFinishing())
            progressDialog.show();

    }

    public void closeProgressDialog() {
        if (null != progressDialog) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected void setParamToSp(String key, Object object) {
        SharedPreferencesUtils.setSpParam(mContext, key, object);
    }

    protected <Type> Type getParamFromSp(String key, Type defaultObject) {
        return SharedPreferencesUtils.getSpParam(mContext, key, defaultObject);
    }

    @Override
    protected void onDestroy() {
        handler = null;
        MLog.i(TAG, "onDestroy");
        closeProgressDialog();
        AppManager.getAppManager().removeActivity(this);
        super.onDestroy();
    }

    public boolean isDestroyedApi8() {
        return handler == null;
    }

    /**
     * 描述：Toast提示文本.
     *
     * @param resId 文本的资源ID
     */
    public void showToast(int resId) {
        ToastUtil.show(this, "" + this.getResources().getText(resId));
    }

    /**
     * 描述：Toast提示文本.
     *
     * @param text 文本
     */
    public void showToast(String text) {
        ToastUtil.show(this, text);
    }

    /**
     * 描述：对话框dialog （确认，取消）.
     *
     * @param title              对话框标题内容
     * @param msg                对话框提示内容
     * @param mOkOnClickListener 点击确认按钮的事件监听
     */
    public void dialogOpen(String title, String msg, DialogInterface.OnClickListener mOkOnClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setPositiveButton("确认", mOkOnClickListener);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 描述：对话框dialog （无按钮）.
     *
     * @param title 对话框标题内容
     * @param msg   对话框提示内容
     */
    public void dialogOpen(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.create().show();
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (null == msg.obj) {
            return true;
        }
        switch (msg.what) {
            case MessageWhat.OPEN_PROGRESS_DIALOG:
                openProgressDialog((String) msg.obj);
                break;
            case MessageWhat.CLOSE_PROGRESS_DIALOG:
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
