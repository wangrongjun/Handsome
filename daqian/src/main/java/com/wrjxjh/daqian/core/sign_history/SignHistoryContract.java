package com.wrjxjh.daqian.core.sign_history;

import com.wrjxjh.daqian.BasePresenter;
import com.wrjxjh.daqian.BaseView;
import com.wrjxjh.daqian.bean.result.SignListResult;

import java.util.List;

/**
 * by wangrongjun on 2017/3/18.
 */
public class SignHistoryContract {

    interface Presenter extends BasePresenter {
        void refresh();

        void loadMore();
    }

    interface View extends BaseView<Presenter> {
        void showRefresh();

        void dismissRefresh();

        void dismissFooterRefresh();


        void showSignList(List<SignListResult> signListResultList);

        void addSignListAndShow(List<SignListResult> signListResultList);
    }

}
