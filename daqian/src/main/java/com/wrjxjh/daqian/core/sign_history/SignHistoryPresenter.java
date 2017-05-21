package com.wrjxjh.daqian.core.sign_history;

import com.wrjxjh.daqian.bean.result.SignListResult;
import com.wrjxjh.daqian.constant.SC;
import com.wrjxjh.daqian.data.remote.IRemoteData;
import com.wrjxjh.daqian.data.remote.RemoteDataImpl;

import java.util.List;

/**
 * by wangrongjun on 2017/3/18.
 */
public class SignHistoryPresenter implements SignHistoryContract.Presenter {

    private IRemoteData remoteData;
    private SignHistoryContract.View signHistoryView;

    private static final int sizeOfPage = 8;//每次请求数据的数目（每个分页的大小）
    private int nextBeginIndex = 0;//下一次刷新请求数据的起始位置（用于实现分页功能）

    public SignHistoryPresenter(SignHistoryContract.View signHistoryView) {
        this.signHistoryView = signHistoryView;
        remoteData = new RemoteDataImpl();
        refresh();
    }

    @Override
    public void refresh() {
        signHistoryView.showRefresh();
        remoteData.getSignedList(0, sizeOfPage,
                new IRemoteData.Callback<List<SignListResult>>() {
                    @Override
                    public void onSuccess(List<SignListResult> signListResultList) {
                        signHistoryView.dismissRefresh();
                        if (signListResultList.size() > 0) {
                            signHistoryView.showSignList(signListResultList);
                            nextBeginIndex = sizeOfPage;
                        } else {
                            signHistoryView.showToastMessage("没有数据");
                        }
                    }

                    @Override
                    public void onFail(int errorCode) {
                        signHistoryView.dismissRefresh();
                        signHistoryView.showToastMessage(SC.getErrorMessage(errorCode));
                    }
                });
    }

    @Override
    public void loadMore() {
        remoteData.getSignedList(nextBeginIndex, sizeOfPage,
                new IRemoteData.Callback<List<SignListResult>>() {
                    @Override
                    public void onSuccess(List<SignListResult> signListResultList) {
                        signHistoryView.dismissFooterRefresh();
                        if (signListResultList.size() > 0) {
                            signHistoryView.addSignListAndShow(signListResultList);
                            nextBeginIndex += sizeOfPage;
                        } else {
                            signHistoryView.showToastMessage("没有更多的数据了");
                        }
                    }

                    @Override
                    public void onFail(int errorCode) {
                        signHistoryView.dismissFooterRefresh();
                        signHistoryView.showToastMessage(SC.getErrorMessage(errorCode));
                    }
                });
    }
}
