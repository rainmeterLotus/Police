package com.police.momo.setting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.police.momo.R;
import com.police.momo.base.BaseActivity;
import com.police.momo.util.CommenUtils;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UpdateStatus;

/**
 * 作者： momo on 2015/9/26.
 * 邮箱：wangzhonglimomo@163.com
 */
public class SettingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        {
            View listitem = findViewById(R.id.listitem_update);
            listitem.setOnClickListener(onClickListener);
            ((TextView) listitem.findViewById(R.id.textView_title)).setText("检查更新");
            ((TextView) listitem.findViewById(R.id.textView_detail)).setText("当前版本:"
                    + CommenUtils.getAppVersionName(getActivity()));
        }
        {
            View listitem = findViewById(R.id.listitem_feedback);
            listitem.setOnClickListener(onClickListener);
            ((TextView) listitem.findViewById(R.id.textView_title)).setText("意见反馈");
        }
        {
            View listitem = findViewById(R.id.listitem_about);
            listitem.setOnClickListener(onClickListener);
            ((TextView) listitem.findViewById(R.id.textView_title)).setText("关于我们");
        }
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.listitem_update:
                    onClickUpdate();
                    break;
                case R.id.listitem_feedback:
                    onClickFeedback();
                    break;
                case R.id.listitem_about:
                    onClickAbout();
                    break;
            }
        }
    };
    /**
     *
     */
    protected void onClickUpdate() {
        final ProgressDialog dialog = ProgressDialog.show(this, null, "正在检查更新");
        dialog.setCancelable(true);
        UmengUpdateAgent.setDefault();
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new com.umeng.update.UmengUpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, com.umeng.update.UpdateResponse updateInfo) {
                dialog.dismiss();
                switch (updateStatus) {
                    case UpdateStatus.Yes: // has update
                        UmengUpdateAgent.showUpdateDialog(getActivity(), updateInfo);
                        break;
                    case UpdateStatus.No: // has no update
                        Toast.makeText(getActivity(), "当前是最新版本", Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.NoneWifi: // none wifi
                        Toast.makeText(getActivity(), "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.Timeout: // time out
                        Toast.makeText(getActivity(), "检查版本超时", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        });
        UmengUpdateAgent.forceUpdate(getActivity());
    }
    /**
     *
     */
    protected void onClickFeedback() {
        FeedbackAgent agent = new FeedbackAgent(this);
        agent.startFeedbackActivity();
    }
    /**
     *
     */
    protected void onClickAbout() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
//        Intent intent = new Intent(getActivity(), WebViewActivity.class);
//        intent.putExtra(WebViewActivity.IntentKey_URL, AppUrlConfig.api.ABOUT_URL);
//        intent.putExtra(WebViewActivity.IntentKey_Title, "关于我们");
//        startActivity(intent);
    }

}
