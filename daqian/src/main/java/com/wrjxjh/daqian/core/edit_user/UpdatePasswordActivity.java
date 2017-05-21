package com.wrjxjh.daqian.core.edit_user;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wang.android_lib.util.DialogUtil;
import com.wang.java_util.TextUtil;
import com.wrjxjh.daqian.BaseActivity;
import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.bean.User;
import com.wrjxjh.daqian.constant.SC;
import com.wrjxjh.daqian.data.local.LocalDataImpl;
import com.wrjxjh.daqian.data.remote.IRemoteData;
import com.wrjxjh.daqian.data.remote.RemoteDataImpl;

/**
 * by wangrongjun on 2017/3/5.
 */
public class UpdatePasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText etOldPassword;
    private EditText etNewPassword;
    private EditText etConfirmNewPassword;
    private Button btnConfirm;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_update_password);
        etOldPassword = (EditText) findViewById(R.id.et_old_password);
        etNewPassword = (EditText) findViewById(R.id.et_new_password);
        etConfirmNewPassword = (EditText) findViewById(R.id.et_confirm_new_password);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
    }

    @Override
    protected void initListener() {
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String oldPassword = etOldPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        String confirmNewPassword = etConfirmNewPassword.getText().toString();
        if (TextUtil.isEmpty(oldPassword, confirmNewPassword, newPassword)) {
            showToast("请输入密码");
        } else if (!newPassword.equals(confirmNewPassword)) {
            showToast("密码不一致");
        } else {
            updatePassword(oldPassword, newPassword);
        }
    }

    private void updatePassword(String oldPassword, String newPassword) {
        User user = new LocalDataImpl().getUser();
        if (user == null) {
            showToast("Error: user==null");
            return;
        }

        DialogUtil.showProgressDialog(this, "正在修改");

        new RemoteDataImpl().updatePassword(user.getUserId(), oldPassword, newPassword,
                new IRemoteData.Callback() {
                    @Override
                    public void onSuccess(Object entity) {
                        DialogUtil.cancelProgressDialog();
                        showToast("密码修改成功");
                        finish();
                    }

                    @Override
                    public void onFail(int errorCode) {
                        DialogUtil.cancelProgressDialog();
                        if (errorCode == SC.ERROR_UPDATE_USER_PASSWORD_PERMISSION) {
                            showToast("原密码不正确");
                        } else {
                            showToast("操作失败，错误代码：" + errorCode);
                        }
                    }
                });
    }
}
