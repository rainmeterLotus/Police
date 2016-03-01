package com.police.momo.welcome;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.police.momo.Constant;
import com.police.momo.R;
import com.police.momo.login.LoginActivity;
import com.police.momo.util.SharedPreferencesUtils;

public class WelcomeActivity extends Activity implements Runnable {

    protected static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView image = new ImageView(this);
        image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        image.setBackgroundResource(R.mipmap.welcome);
        setContentView(image);
        new Thread(this).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1300);
            judgeVersion();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void judgeVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            int vercode = info.versionCode;
            int spVercode = (Integer) SharedPreferencesUtils.getSpParam(this, Constant.VERSION_CODE, 0);
            if (spVercode == vercode) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                this.finish();
            } else {
                SharedPreferencesUtils.setSpParam(this, Constant.VERSION_CODE, vercode);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                this.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
