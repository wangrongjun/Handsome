package com.wrjxjh.daqian.core.register;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.wang.android_lib.util.DialogUtil;
import com.wang.android_lib.util.M;
import com.wang.java_util.TextUtil;
import com.wrjxjh.daqian.BaseActivity;
import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.bean.User;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etNickname;
    private RadioButton rbMan;
    private Button btnRegister;

    private RegisterContract.Presenter presenter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_register);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etConfirmPassword = (EditText) findViewById(R.id.et_confirm_password);
        etNickname = (EditText) findViewById(R.id.et_nickname);
        rbMan = (RadioButton) findViewById(R.id.rb_man);
        btnRegister = (Button) findViewById(R.id.btn_register);
    }

    @Override
    protected void initListener() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new RegisterPresenter(this);
    }

    private void register() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        String nickname = etNickname.getText().toString();
        int gender = rbMan.isChecked() ? User.GENDER_MAN : User.GENDER_WOMAN;
        if (TextUtil.isEmpty(username, password, confirmPassword)) {
            showToastMessage("不能为空");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showToastMessage("输入的密码不一致");
            return;
        }
        presenter.register(username, password, nickname, gender);
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
        M.t(this, message);
    }

    @Override
    public void returnToLoginPage(String username, String password) {
        Intent data = new Intent();
        data.putExtra("username", username);
        data.putExtra("password", password);
        setResult(0, data);
        finish();
    }
}
