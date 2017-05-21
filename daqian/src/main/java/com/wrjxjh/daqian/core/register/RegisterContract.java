package com.wrjxjh.daqian.core.register;

import com.wrjxjh.daqian.BasePresenter;
import com.wrjxjh.daqian.BaseView;

/**
 * by wangrongjun on 2017/3/8.
 */
public interface RegisterContract {

    interface Presenter extends BasePresenter {
        void register(String username, String password, String nickname, int gender);
    }

    interface View extends BaseView<Presenter> {
        void returnToLoginPage(String username, String password);
    }

}
