package com.wrjxjh.daqian.core.login;

import com.wrjxjh.daqian.BasePresenter;
import com.wrjxjh.daqian.BaseView;

/**
 * by wangrongjun on 2017/3/8.
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void navigateRegisterPage();

        void navigateMainPage();
    }

    interface Presenter extends BasePresenter {
        void login(String username, String password);

        void navigateRegisterPage();
    }

}
