package com.police.momo.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.police.momo.MainActivity;
import com.police.momo.R;
import com.police.momo.base.BaseActivity;

/**
 * Created by momo on 2015/9/13.
 */
public class LoginActivity extends BaseActivity {

    private EditText input_userName;
    private EditText input_password;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startActivity(MainActivity.class);
        initView();
    }

    private void initView() {
        input_userName = (EditText) this.findViewById(R.id.input_userName);
        input_password = (EditText) this.findViewById(R.id.input_password);
        login_btn = (Button) this.findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.login_btn) {
            login();
        }
    }

    private void login() {
        String userName = input_userName.getText().toString().trim();
        String userPass = input_password.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            showToast("请输入用户名！");
            return;
        }
        if (TextUtils.isEmpty(userPass)) {
            showToast("请输入密码！");
            return;
        }
        if ("1".equals(userName) && "1".equals(userPass)) {
            showToast("登录成功");
            startActivity(MainActivity.class);
        }
    }
}
