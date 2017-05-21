package com.wrjxjh.daqian.core.login;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.android_lib.util.DialogUtil;
import com.wrjxjh.daqian.BaseActivity;
import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.core.register.RegisterActivity;
import com.wrjxjh.daqian.extra.activity.DaqianMainActivity;

public class LoginActivity extends BaseActivity implements OnClickListener, LoginContract.View {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView btnRegister;
    private TextView tvTest1;
    private TextView tvTest2;
    private TextView tvTest3;

    private LoginPresenter presenter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.et_login_username);
        etPassword = (EditText) findViewById(R.id.et_login_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (TextView) findViewById(R.id.btn_login_register);
        tvTest1 = (TextView) findViewById(R.id.tv_test_1);
        tvTest2 = (TextView) findViewById(R.id.tv_test_2);
        tvTest3 = (TextView) findViewById(R.id.tv_test_3);
    }

    @Override
    protected void initListener() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvTest1.setOnClickListener(this);
        tvTest2.setOnClickListener(this);
        tvTest3.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        presenter = new LoginPresenter(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            presenter.login(username, password);
        } else if (view.getId() == R.id.btn_login_register) {
            presenter.navigateRegisterPage();

        } else if (view.getId() == R.id.tv_test_1) {
            etUsername.setText("wang");
            etPassword.setText("123");

        } else if (view.getId() == R.id.tv_test_2) {
            etUsername.setText("rong");
            etPassword.setText("123");

        } else if (view.getId() == R.id.tv_test_3) {
            etUsername.setText("jun");
            etPassword.setText("123");

        }
    }

    // 注册界面注册成功的用户名和密码返回到登陆界面
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String username = "";
        String password = "";
        if (data != null) {
            username = data.getStringExtra("username");
            password = data.getStringExtra("password");
        }
        if (username.length() > 0) {
            etUsername.setText(username);
        }
        if (password.length() > 0) {
            etPassword.setText(password);
        }
    }

    @Override
    public void showProgressIndicator(String hint) {
        DialogUtil.showProgressDialog(this, hint);
    }

    @Override
    public void dismissProgressIndicator() {
        DialogUtil.cancelProgressDialog();
    }

    @Override
    public void showToastMessage(String message) {
//        Snackbar.make(etPassword, message, Snackbar.LENGTH_LONG);
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateRegisterPage() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void navigateMainPage() {
        Intent intent = new Intent(this, DaqianMainActivity.class);
        startActivity(intent);
        finish();
    }
}
