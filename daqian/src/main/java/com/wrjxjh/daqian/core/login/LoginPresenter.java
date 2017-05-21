package com.wrjxjh.daqian.core.login;

import com.wrjxjh.daqian.bean.User;
import com.wrjxjh.daqian.constant.SC;
import com.wrjxjh.daqian.data.local.ILocalData;
import com.wrjxjh.daqian.data.remote.IRemoteData;
import com.wrjxjh.daqian.data.local.LocalDataImpl;
import com.wrjxjh.daqian.data.remote.RemoteDataImpl;

/**
 * by wangrongjun on 2017/3/4.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private ILocalData localData;
    private IRemoteData remoteData;
    private LoginContract.View loginView;

    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = loginView;
        localData = new LocalDataImpl();
        remoteData = new RemoteDataImpl();
    }

    @Override
    public void login(String username, String password) {
        loginView.showProgressIndicator("正在登录");
        remoteData.login(username, password, new IRemoteData.Callback<User>() {
            @Override
            public void onSuccess(User entity) {
                loginView.dismissProgressIndicator();
                localData.setUser(entity);
                loginView.navigateMainPage();
            }

            @Override
            public void onFail(int errorCode) {
                loginView.dismissProgressIndicator();

                if (errorCode == SC.ERROR_LOGIN_USERNAME_NOT_EXISTS) {
                    loginView.showToastMessage("登录失败，用户名不存在");
                } else if (errorCode == SC.ERROR_LOGIN_PASSWORD) {
                    loginView.showToastMessage("登录失败，密码错误");
                } else {
                    loginView.showToastMessage(SC.getErrorMessage(errorCode));
                }
            }
        });

    }

    @Override
    public void navigateRegisterPage() {
        loginView.navigateRegisterPage();
    }

}
