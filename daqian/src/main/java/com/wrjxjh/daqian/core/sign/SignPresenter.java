package com.wrjxjh.daqian.core.sign;

import com.wrjxjh.daqian.bean.result.SignListResult;
import com.wrjxjh.daqian.constant.SC;
import com.wrjxjh.daqian.data.local.ILocalData;
import com.wrjxjh.daqian.data.local.LocalDataImpl;
import com.wrjxjh.daqian.data.remote.IRemoteData;
import com.wrjxjh.daqian.data.remote.RemoteDataImpl;

import java.util.List;

/**
 * by wangrongjun on 2017/3/5.
 */
public class SignPresenter implements SignContract.Presenter {

    private SignContract.View signView;
    private ILocalData localData;
    private IRemoteData remoteData;

    public SignPresenter(SignContract.View signView) {
        this.signView = signView;
        localData = new LocalDataImpl();
        remoteData = new RemoteDataImpl();
        startGetSignState();
        refreshSignList();
    }

    private void startGetSignState() {
        int userId = localData.getUser().getUserId();
        remoteData.isTodaySigned(userId, new IRemoteData.Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean entity) {
                signView.showTodaySignState(entity);
            }

            @Override
            public void onFail(int errorCode) {
                signView.showToastMessage(SC.getErrorMessage(errorCode));
            }
        });
    }

    @Override
    public void sign(final String signText) {
        signView.showProgressIndicator("正在签到");
        int userId = localData.getUser().getUserId();
        remoteData.sign(userId, signText, new IRemoteData.Callback() {
            @Override
            public void onSuccess(Object entity) {
                signView.dismissProgressIndicator();
                signView.showTodaySignState(true);
                signView.showToastMessage("签到成功");
                refreshSignList();
            }

            @Override
            public void onFail(int errorCode) {
                signView.dismissProgressIndicator();
                if (errorCode == SC.ERROR_ALREADY_SIGNED) {
                    signView.showToastMessage("你今天已经签到");
                    signView.showTodaySignState(true);
                } else {
                    signView.showToastMessage(SC.getErrorMessage(errorCode));
                    signView.showTodaySignState(false);
                }
            }
        });
    }

    @Override
    public void refreshSignList() {
        signView.showRefresher();
        remoteData.getTodaySignedList(new IRemoteData.Callback<List<SignListResult>>() {
            @Override
            public void onSuccess(List<SignListResult> entity) {
                signView.hideRefresher();
                signView.showSignList(entity);
            }

            @Override
            public void onFail(int errorCode) {
                signView.hideRefresher();
                signView.showToastMessage(SC.getErrorMessage(errorCode));
            }
        });
    }
}
