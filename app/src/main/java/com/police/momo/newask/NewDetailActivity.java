package com.police.momo.newask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.police.momo.base.BaseActivity;

/**
 * 作者： momo on 2015/11/10.
 * 邮箱：wangzhonglimomo@163.com
 */
public class NewDetailActivity extends BaseActivity {
    public final static String TITLE = "title";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, NewDetailActivity.class);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            String title = getIntent().getStringExtra(TITLE);
            setTitle(title);
        }
    }
}
