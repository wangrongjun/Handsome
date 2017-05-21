package com.wrjxjh.daqian.core.register;

import com.wrjxjh.daqian.bean.User;
import com.wrjxjh.daqian.constant.SC;
import com.wrjxjh.daqian.data.remote.IRemoteData;
import com.wrjxjh.daqian.data.remote.RemoteDataImpl;

/**
 * by wangrongjun on 2017/3/5.
 */
public class RegisterPresenter implements RegisterContract.Presenter {

    private IRemoteData remoteData;
    private RegisterContract.View registerView;

    public RegisterPresenter(RegisterContract.View registerView) {
        this.registerView = registerView;
        remoteData = new RemoteDataImpl();
    }

    @Override
    public void register(final String username, final String password, String nickname, int gender) {
        registerView.showProgressIndicator("正在注册");
        User user = new User(username, password, gender, nickname);
        remoteData.register(user, new IRemoteData.Callback() {
            @Override
            public void onSuccess(Object entity) {
                registerView.dismissProgressIndicator();
                registerView.showToastMessage("注册成功");
                registerView.returnToLoginPage(username, password);
            }

            @Override
            public void onFail(int errorCode) {
                registerView.dismissProgressIndicator();
                if (errorCode == SC.ERROR_REGISTER_USER_EXISTS) {
                    registerView.showToastMessage("注册失败，用户已存在");
                } else {
                    registerView.showToastMessage(SC.getErrorMessage(errorCode));
                }
            }
        });
    }

}
