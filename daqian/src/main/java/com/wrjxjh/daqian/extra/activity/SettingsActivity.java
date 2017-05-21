package com.wrjxjh.daqian.extra.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.wang.android_lib.util.DialogUtil;
import com.wrjxjh.daqian.BaseActivity;
import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.core.edit_user.UpdatePasswordActivity;
import com.wrjxjh.daqian.core.login.LoginActivity;
import com.wrjxjh.daqian.data.local.LocalDataImpl;

public class SettingsActivity extends BaseActivity implements
        OnClickListener {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_settings);

        // menu菜单的返回键和标题
        ((TextView) findViewById(R.id.menu_back_tv)).setText("设置");
        findViewById(R.id.menu_btn_back).setOnClickListener(this);

        View btnUpdatePassword = findViewById(R.id.btn_settings_update_password);
        ((TextView) btnUpdatePassword.findViewById(R.id.btn_tv_item))
                .setText("修改密码");
        btnUpdatePassword.setOnClickListener(this);

        View btnAbout = findViewById(R.id.btn_settings_about);
        ((TextView) btnAbout.findViewById(R.id.btn_tv_item)).setText("关于大签");
        btnAbout.setOnClickListener(this);

        View btnExit = findViewById(R.id.btn_settings_exit);
        ((TextView) btnExit.findViewById(R.id.btn_tv_item)).setText("退出帐号");
        btnExit.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_settings_update_password) {
            Intent intent1 = new Intent(this, UpdatePasswordActivity.class);
            startActivity(intent1);

        } else if (v.getId() == R.id.btn_settings_about) {
            Intent intent2 = new Intent(this, AboutActivity.class);
            startActivity(intent2);

        } else if (v.getId() == R.id.btn_settings_exit) {
            confirmExit();

        } else if (v.getId() == R.id.menu_btn_back) {
            finish();

        }

    }

    private void confirmExit() {
        DialogUtil.showConfirmDialog(this, "确认注销", "确实要注销当前帐户吗？",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LocalDataImpl localData = new LocalDataImpl();
                        localData.clearAll();
                        finishAll();
                        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
    }

}
