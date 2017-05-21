package com.wrjxjh.daqian.core.sign;

import com.wrjxjh.daqian.BasePresenter;
import com.wrjxjh.daqian.BaseView;
import com.wrjxjh.daqian.bean.result.SignListResult;

import java.util.List;

/**
 * by wangrongjun on 2017/3/8.
 */
public interface SignContract {

    interface Presenter extends BasePresenter {
        void sign(String signText);

        void refreshSignList();
    }

    interface View extends BaseView<Presenter> {
        void showRefresher();

        void hideRefresher();

        void showSignList(List<SignListResult> signListResultList);

        void showTodaySignState(boolean isTodaySign);
    }

}
